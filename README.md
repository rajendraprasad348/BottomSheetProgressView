# BottomSheetProgressView
This repository mainly used to show the progress dialog.

# Version
 [![](https://jitpack.io/v/rajendraprasad348/BottomSheetProgressView.svg)](https://jitpack.io/#rajendraprasad348/BottomSheetProgressView)


# Description

 Simple example shown in app section

An BottomSheetProgressView is a customized logo rotatable progress view. Can be used it as a 'animated mode' or 'Logo rotatable mode'. 

 # Demo

  https://youtu.be/NP-jAPYvM1M

# Installation

      Step1:  Add it in your root build.gradle at the end of repositories:
      
           allprojects {
		repositories {
			//...
			maven { url 'https://jitpack.io' }
		             }
	             }  
	
	  Step2:  Add the dependency
    
         dependencies {
               //....
	       	     implementation 'com.github.rajendraprasad348:BottomSheetProgressView:1.0'
	       } 
  
 
 # Download APK
   https://www.dropbox.com/s/ro6wkw2k52cvph3/BottomSheetProgressView.apk?dl=0 
   
  # Usage
  
  Add this View in .xml file for your activity.
  
          <com.rajendra_prasad.advancedprogressviewlibrary.BottomSheetProgressDialog
           android:id="@+id/custom_progess"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           app:BottomSheetProgress_BackgroundColor="#91C0E0"
           app:BottomSheetProgress_Font_Face="NORMAL"
           app:BottomSheetProgress_Logo_Rotate="true"
           app:BottomSheetProgress_Message="Loading......"
           app:BottomSheetProgress_ProgressBar_Color="#80000000"
           app:BottomSheetProgress_Cancellable="true"
           app:BottomSheetProgress_Icon="@drawable/signup_form_patterns"
           app:BottomSheetProgress_TextColor="#000000"
           app:BottomSheetProgress_Canceled_On_Touch_Outside="false"
           app:BottomSheetProgress_TextSize="10dp" />
