/**
 * This controller is used to detect user's face in real time. Used the face to match user in database.
 * Training code is also included. Admin can increase the amount of training images to promote the accuracy of the model
 * Emotion can also be detected by using Google Cloud Vision API.
 * If the model detect the exist users, click confirm button will lead users to their dashboard
 * If the model detect new user, click confirm button will lead him or her to input their info and then show their dashboard.
 */
package application;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.Algorithm;
import org.opencv.core.Core;
import org.opencv.face.*;
import org.opencv.face.Face;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobListOption;
import com.google.cloud.storage.StorageOptions;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;

import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.FaceAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.GcsDestination;
import com.google.cloud.vision.v1.GcsSource;
//import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;

import com.google.protobuf.ByteString;

import com.google.protobuf.util.JsonFormat;
import sun.misc.*;

/**
 * Created by Eclipse
 * @author zimoyang, Chenlu Jiang
 * @Date:2019/11/20
 */

public class FXController {
	private int label;//label is to identify student id 
	private String name_1;// name_1 is to identify student name
	static int count=1;
	@FXML
	private Button start_btn;
	@FXML
	private ImageView currentFrame;
	
	@FXML
	private Label lblDisplay;
	
	@FXML
	private Button btConfirm;
	
	private Pane rootElement;
	// a timer for acquiring the video stream
	private ScheduledExecutorService timer;
	// the OpenCV object that performs the video capture
	private VideoCapture capture;
	// a flag to change the button behavior
	private boolean cameraActive;
	// face cascade classifier
	private CascadeClassifier faceCascade;
	private int absoluteFaceSize;
	
	public int index = 0;
	public int ind = 0;
	// New user Name for a training data
	public String newname;
	// Random number of a training set
	public int random = (int )(Math.random() * 20 + 3);
	// Names of the people from the training set
	public HashMap<Integer, String> names = new HashMap<Integer, String>();
	
//	private LBPHFaceRecognizer  faceRecognizer = LBPHFaceRecognizer.create(1,8,8,8,100);
	private FisherFaceRecognizer faceRecognizer=FisherFaceRecognizer.create(0,10000);
//	private EigenFaceRecognizer faceRecognizer=EigenFaceRecognizer.create(0,25000);
	
	/**
	 * Init the controller, at start time
	 */
	
	public void init()
	{
		this.capture = new VideoCapture();
		this.faceCascade = new CascadeClassifier();
		this.absoluteFaceSize = 0;
		btConfirm.setVisible(false);
		// Takes some time thus use only when training set
		// was updated 
		trainModel();
	}
	@FXML
	protected void startCamera()
	{
		// set a fixed width for the frame
		currentFrame.setFitWidth(600);
		// preserve image ratio
		currentFrame.setPreserveRatio(true);
		
		if (!this.cameraActive)
		{
			//load face detection model
			this.faceCascade.load("resources/haarcascade_frontalface_alt.xml");
			this.start_btn.setDisable(false);
			
			// start the video capture
			this.capture.open(0);
			
			// is the video stream available?
			if (this.capture.isOpened())
			{
				this.cameraActive = true;
				
				// grab a frame every 33 ms (30 frames/sec)
				Runnable frameGrabber = new Runnable() {
					
					@Override
					public void run()
					{
						Image imageToShow = grabFrame();
						currentFrame.setImage(imageToShow);
					}
				};
				
				this.timer = Executors.newSingleThreadScheduledExecutor();
				this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
				
				// update the button content
				this.start_btn.setText("Stop Camera");
			}
			else
			{
				// log the error
				System.err.println("Failed to open the camera connection...");
			}
		}
		else
		{
			
			lblDisplay.setText(name_1); 
			btConfirm.setVisible(true);
			// the camera is not active at this point
			this.cameraActive = false;
			// update again the button content
			this.start_btn.setText("Start Camera");			
			// stop the timer
			try{
				this.timer.shutdown();
				this.timer.awaitTermination(330000, TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException e)
			{
				// log the exception
				System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
			}
			
			// release the camera
			this.capture.release();
			// clean the frame
			this.currentFrame.setImage(null);
			
			// Clear the parameters for new user data collection
			index = 0;
			newname = "";
		}
	}
	
	private Image grabFrame()
	{
		Image imageToShow=null;
		Mat frame=new Mat();
		//check if the capture is open
		if(this.capture.isOpened())
		{
			try
			{
				//read the current time
				this.capture.read(frame);
				int index=0;		
				//if the frame is not empty, process it
				if(!frame.empty())
				{
					//convert the image to gray scale
					getFace(frame);
					//convert the Mat object(OpenCV)to Image (JavaFX)
					imageToShow=mat2Image(frame);
				}
			}catch(Exception e)
			{
				//log the error
				System.err.println("ERROR: "+e.getMessage());
			}
		}
		return imageToShow;
	}
	
	private void transform(Mat img,String filename) {
		MatOfRect faceDetections=new MatOfRect();
    	
    	CascadeClassifier faceDetector = new CascadeClassifier("./resources/haarcascade_frontalface_alt.xml");
    	faceDetector.detectMultiScale(img, faceDetections, 1.1, 5, Objdetect.CASCADE_SCALE_IMAGE);
    	Rect[] facesArray = faceDetections.toArray(); 
    	for (int i = 0; i < facesArray.length; i++) {
			Imgproc.rectangle(img, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0), 3);
			// Crop the detected faces
			Rect rectCrop = new Rect(facesArray[i].tl(), facesArray[i].br());
			Mat croppedImage = new Mat(img, rectCrop);
			// Change to gray scale
			Imgproc.cvtColor(croppedImage, croppedImage, Imgproc.COLOR_BGR2GRAY);
			// Equalize histogram
			Imgproc.equalizeHist(croppedImage, croppedImage);
			// Resize the image to a default size
			Mat resizeImage = new Mat();
			Size size = new Size(150,150);
			Imgproc.resize(croppedImage, resizeImage, size);
			Imgcodecs.imwrite("resources/train_models/"+filename+".jpg",resizeImage);
	}
	}
	/**
	 * This method is to train the model 
	 */
	private void trainModel() {
		// Read the data from the training set
				File root = new File("resources/train_images/");
				FilenameFilter imgFilter = new FilenameFilter() {
		            public boolean accept(File dir, String name) {
		                name = name.toLowerCase();
		                return name.endsWith(".jpg");
		            }
		        };        
		        /*
		         * These code is used for training.
		         * If you do not need to train, please comment them.
		         */
//		        for(int j=0;j<imageFiles.length;j++) {
//////		        	 Parse the training set folder files
//		        	Mat img = Imgcodecs.imread(imageFiles[j].getAbsolutePath());
//		        	
//		        	transform(img,imageFiles[j].getName());
//		        }
		        
		        //get new root from models
		        File root2 = new File("resources/train_models/");
		        //use filters to filter photo that fits the format
		        FilenameFilter imgFilter2 = new FilenameFilter() {
		            public boolean accept(File dir, String name) {
		                name = name.toLowerCase();
		                return name.endsWith(".jpg");
		            }
		        };
		        //imageFiles2 is File array that save all the photo file after filtering
		        File[] imageFiles2 = root2.listFiles(imgFilter2);     
		        //images2 saved photos in train_set
		        List<Mat> images2 = new ArrayList<Mat>();
		        Mat labels = new Mat(imageFiles2.length,1,CvType.CV_32SC1);     
		        int counter = 0;
		        for(int i=0;i<imageFiles2.length;i++)
		        {
		        	
		        	Mat img2=Imgcodecs.imread(imageFiles2[i].getAbsolutePath());
		        	// Change to Grayscale and equalize the histogram
		        	Imgproc.cvtColor(img2, img2, Imgproc.COLOR_BGR2GRAY);
		        	Imgproc.equalizeHist(img2, img2);
		        	// Extract label from the file name
		        	int label = Integer.parseInt(imageFiles2[i].getName().split("\\-")[0]);
		        	// Extract name from the file name and add it to names HashMap
		        	String labnname = imageFiles2[i].getName().split("\\_")[0];
		        	String name = labnname.split("\\-")[1];
		        	names.put(label, name);
		        	// Add training set images to images Mat
		        	images2.add(img2);

		        	labels.put(counter, 0, label);
		        	counter++;
		        }
/*
 * These codes are also only used in training. No need to use them again after training. 
 */
//		        	faceRecognizer.train(images2, labels);
//		        	faceRecognizer.write("C:/Users/zimoyang/Documents/Unknown2.xml");
	}
	/**
	 * 
	 * @param currentFace is the mat that includes face in real time
	 * @param outfile is the file path that stores current face
	 * @return a String array that includes the predict result, confidence and three emotions.
	 */
	
	private String[] faceRecognition(Mat currentFace,String outfile) { 	
    	// predict the label   	
    	int[] predLabel = new int[1];
        double[] confidence = new double[1];
        int result = -1;
        //Read XML file for face detection in OpenCV
        faceRecognizer.read("resources/Fisher.xml");
    	faceRecognizer.predict(currentFace,predLabel,confidence);
    	result = faceRecognizer.predict_label(currentFace);

    	ArrayList<String> emotions=new ArrayList<>();
    	//save image into base64 format
    	//so that Google Cloud API will be faster
    	outfile=GetImageStr();
    	outfile=GenerateImage(outfile);
    	
    	Imgcodecs.imwrite(outfile,currentFace);
    	try {
    		//get emotions
    		emotions = detectFaces(new String[] {outfile});
    		} catch (Exception e) {
    			e.printStackTrace();
    		}  	
    	return new String[] {String.valueOf(result),String.valueOf(confidence[0]),emotions.get(0),emotions.get(1),emotions.get(2)};
	}
	/**
	 * 
	 * @param frame this the Mat object(OpenCV) that need to be converted into Image (JavaFX)
	 * @return an JPG image after encoding
	 */
	private Image mat2Image(Mat frame)
	{
		//create a temporary buffer
		MatOfByte buffer=new MatOfByte();
		//encode the frame in the buffer
		Imgcodecs.imencode(".jpg",frame,buffer);
		//build and return an Image created from the image encoded in the buffer
		return new Image(new ByteArrayInputStream(buffer.toArray()));
		
	}
	public void setRootElement(Pane root) {
		this.rootElement=root;
	}
	/**
	 * 
	 * @param frame this frame is what camera has captured
	 */
	public void getFace(Mat frame) {
		//if there is a new user. Saved his or her face before processing the image
		saveNewUser(frame);
		//convert face into gray scale
		MatOfRect face=new MatOfRect();
		Mat grayFrame=new Mat();
		Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(grayFrame, grayFrame);
		// compute minimum face size (15% of the frame height, in our case)
		if (this.absoluteFaceSize == 0)
		{
			int height = grayFrame.rows();
			if (Math.round(height * 0.15f) > 0)
			{
				this.absoluteFaceSize = Math.round(height * 0.15f);
			}
		}
		this.faceCascade.detectMultiScale(grayFrame, face,1.1,5,0 | Objdetect.CASCADE_SCALE_IMAGE,new Size(this.absoluteFaceSize,this.absoluteFaceSize),new Size());
		//Match features
		Rect[] rects=face.toArray();
		if(rects!=null&&rects.length>=1) {
			for(int x=0;x<rects.length;x++) {
				/*draw rectangle
				*/
				Imgproc.rectangle(frame, rects[x].tl(), rects[x].br(),new Scalar(0,0,255),3);
				Rect rectCrop=new Rect(rects[x].tl(),rects[x].br());
				Mat croppedImage=new Mat(frame,rectCrop);
				// Change to gray scale
				Imgproc.cvtColor(croppedImage, croppedImage, Imgproc.COLOR_BGR2GRAY);
				// Equalize histogram
				Imgproc.equalizeHist(croppedImage, croppedImage);
				// Resize the image to a default size
				Mat resizeImage = new Mat();
				Size size = new Size(150,150);
				Imgproc.resize(croppedImage, resizeImage, size);
				//returnedResults is to fetch the result of recognition
				String[] returnedResults = faceRecognition(resizeImage,save(resizeImage,rects[x]));
				double prediction = Double.parseDouble(returnedResults[0]);
				double confidence = Double.parseDouble(returnedResults[1]);
				confidence=Math.round(confidence);
				String emotion1=returnedResults[2];
				String emotion2=returnedResults[3];
				String emotion3=returnedResults[4];
				System.out.println(emotion1);
				System.out.println(confidence);

				label = (int) prediction;
				if (names.containsKey(label) && confidence<1500) {// if confidence below 2000, then mark the person as a new user
					name_1 = names.get(label);
				} else {
					name_1 = "Unknown";
					label = 0;
				}
				// Create the text we will annotate the box with:
	            String box_text = "Prediction = " + name_1 + " Confidence = " + confidence;
	            String box_text2="Emotion= "+emotion1;
	            String box_text3="Emotion= "+emotion2;
	            String box_text4="Emotion= "+emotion3;
	            // Calculate the position for annotated text (make sure we don't
	            // put illegal values in there):
	            double pos_x = Math.max(rects[x].tl().x - 10, 0);
	            double pos_y = Math.max(rects[x].tl().y - 10, 0);
	            double pos_x_1 = Math.max(rects[x].tl().x - 35, 0);
	            double pos_y_1 = Math.max(rects[x].tl().y - 35, 0);
	            double pos_x_2 = Math.max(rects[x].tl().x - 55, 0);
	            double pos_y_2 = Math.max(rects[x].tl().y - 55, 0);
	            double pos_x_3 = Math.max(rects[x].tl().x - 75, 0);
	            double pos_y_3 = Math.max(rects[x].tl().y - 75, 0);
	            // And now put it into the image:
	            Imgproc.putText(frame, box_text, new Point(pos_x, pos_y), 
	            Imgproc.FONT_HERSHEY_PLAIN, 2.0, new Scalar(71, 99, 255, 2.0));
	            Imgproc.putText(frame, box_text2, new Point(pos_x_1, pos_y_1), 
	            Imgproc.FONT_HERSHEY_PLAIN, 2.0, new Scalar(71, 99, 255, 2.0));
	            Imgproc.putText(frame, box_text3, new Point(pos_x_2, pos_y_2), 
	            Imgproc.FONT_HERSHEY_PLAIN, 2.0, new Scalar(71, 99, 255, 2.0));
	            Imgproc.putText(frame, box_text4, new Point(pos_x_3, pos_y_3), 
	            Imgproc.FONT_HERSHEY_PLAIN, 2.0, new Scalar(71, 99, 255, 2.0));
	       
	            System.out.println(name_1+" Welcome!");
	            System.out.println(label);
			}
		}		
	}
	/**
	 * 
	 * @param filePath All the file path of training photos.
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public static ArrayList<String> detectFaces(String[] filePath) throws Exception, IOException {
		  List<AnnotateImageRequest> requests = new ArrayList<>();
		  ArrayList<String> result = new ArrayList<>();
		  for(String file:filePath) {
		  ByteString imgBytes = ByteString.readFrom(new FileInputStream(file));

		  com.google.cloud.vision.v1.Image img = com.google.cloud.vision.v1.Image.newBuilder().setContent(imgBytes).build();
		  Feature feat = Feature.newBuilder().setType(Type.FACE_DETECTION).build();
		  AnnotateImageRequest request =
		  AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		  requests.add(request);
		  }

		  try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
		    BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
		    List<AnnotateImageResponse> responses = response.getResponsesList();

		    for (AnnotateImageResponse res : responses) {
		      if (res.hasError()) {
		        return null;
		      }

		      // For full list of available annotations, see http://g.co/cloud/vision/docs
		      for (FaceAnnotation annotation : res.getFaceAnnotationsList()) {
		    	  result.add(" joy: " + annotation.getJoyLikelihood());
		          result.add(" anger: " + annotation.getAngerLikelihood());
		          result.add(" surprise: " + annotation.getSurpriseLikelihood());
		      }
		    }
		  }
		  return result;
		}
	
	/**
	 * 
	 * @param img is the mat that need to be saved as jpg image
	 * @param rect is the size of the mat
	 * @return a path of images
	 */
	private static String save(Mat img,Rect rect) {
		String outFile="resources/current.jpg";
		Imgcodecs.imwrite(outFile, img);
		return outFile;
	}
	/**
	 * 
	 * @param img is the mat that need to be saved as jpg image(this is for the new user)
	 */
	private static void saveNewUser(Mat img) {
		JDBCController db = new JDBCController();
//		Random random=new Random();
//		int count=1;
		String outFile="pic/new_user"+count+".jpg";
		count++;
		Imgcodecs.imwrite(outFile,img);
		
	}
	
	public static String GetImageStr()  
    {//Transfer image file into byte array string and use base64 to encode it
  String imgFile="resources/current.jpg";
  InputStream in=null;
  byte[] data=null;
  try {
   in=new FileInputStream(imgFile);
   data=new byte[in.available()];
   in.read(data);
   in.close();
  }
  catch(IOException e) {
   e.printStackTrace();
  }
  BASE64Encoder encoder=new BASE64Encoder();
  return encoder.encode(data);
    }
	/**
	 * 
	 * @param imgStr is the path that stores all the file. These file need to be decoded from Base64 to images.
	 * @return
	 */
 public static String GenerateImage(String imgStr)
 {
  if(imgStr==null)
   return "error";
  BASE64Decoder decoder=new BASE64Decoder();
  try
  {
	   byte[]b =decoder.decodeBuffer(imgStr);
	   for(int i=0;i<b.length;++i) {
	    if(b[i]<0) {
	     b[i]+=256;
	    }
	   }
	   String imgFilePath="resources/current2.jpg";
	   OutputStream out=new FileOutputStream(imgFilePath);
	   out.write(b);
	   out.flush();
	   out.close();
	   return imgFilePath;
	   
	  }catch(Exception e)
	  {
	   return "error";
	  }
	  
	 }

	
	/**
	 * The handle method for confirm button, 
	 * once clicked it transfers to new user scene if recognize as unknown,
	 * else it transfers to info scene
	 * 
	 * @param event
	 */
	@FXML
	  void btConfirmHandle(ActionEvent event) {

	   try {	
		   if(label==0) {
			    FXMLLoader loader=new FXMLLoader(getClass().getResource("Newuserscene.fxml"));
				AnchorPane root=(AnchorPane)loader.load();
				Scene scene = new Scene(root);
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		        stage.hide();
		        stage.setScene(scene);
		        stage.show();   
		   } else {
			    FXMLLoader loader=new FXMLLoader(getClass().getResource("Infoscene.fxml"));
				AnchorPane root=(AnchorPane)loader.load();
				InfoController info=loader.getController();
				info.setID(label);
				Scene scene = new Scene(root);
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		        stage.hide();
		        stage.setScene(scene);
		        stage.show();
		   }
	         } catch (IOException ex) {
	             System.err.println(ex);
	        }
	}

}
