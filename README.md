# Fluper-Assignment

Libraries Used:

*  ViewModel : (```androidx.lifecycle:lifecycle-viewmodel-ktx:```)

   Description:
   ViewModel interacts with model and also prepares observable(s) that can be observed by a View.
   ViewModel can optionally provide hooks for the view to pass events to the model. 
   One of the important implementation strategies of this layer is to decouple it from the View, i.e,
   ViewModel should not be aware about the view who is interacting with.

*  LiveData : (```androidx.lifecycle:lifecycle-livedata-ktx:```)

   Description:
   LiveData is an observable data holder class. Unlike a regular observable, LiveData is
   lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities,
   fragments, or services. This awareness ensures LiveData only updates app component observers
   that are in an active lifecycle state.

*  Annotation processor: (```kapt "androidx.lifecycle:lifecycle-compiler:```)

   Description:
   To add a dependency on a library that is an annotation processor, you must add it to the
   annotation processor classpath using the annotationProcessor configuration. That's because
   using this configuration improves build performance by separating the compile classpath
   from the annotation processor classpath.

*  Room : (```androidx.room:room-runtime:
           androidx.room:room-compiler:``` )

   Description:
   The Room persistence library provides an abstraction layer over SQLite to allow for more robust
   database access while harnessing the full power of SQLite.
   The library helps you create a cache of your app's data on a device that's running your app.
   This cache, which serves as your app's single source of truth, allows users to view a consistent
   copy of key information within your app, regardless of whether users have an internet connection.

*  Coroutines : (```org.jetbrains.kotlinx:kotlinx-coroutines-core:
                 org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9```)

   Description:
   Coroutines are able to perform long-running and intensive tasks by suspending execution without
   blocking the thread and then resuming the execution at some later time. It allows the creation
   of non-blocking asynchronous code that appears to be synchronous.

*  SDP : (```com.intuit.sdp:sdp-android:```)

   Description:
   An android SDK that provides a new size unit - sdp (scalable dp). This size unit scales with the
   screen size. It can help Android developers with supporting multiple screens.
   for text views please refer to ssp which is based on the sp size unit for texts.

*  Glide : (```com.github.bumptech.glide:glide:
            com.github.bumptech.glide:compiler:```)

   Description:
   Glide is a fast and efficient open source media management and image loading framework for
   Android that wraps media decoding, memory and disk caching, and resource pooling into a
   simple and easy to use interface.
   Glide supports fetching, decoding, and displaying video stills, images, and animated GIFs. Glide
   includes a flexible API that allows developers to plug in to almost any network stack.

*  CardView: (```androidx.cardview:cardview:1.0.0```)

   Description:
   CardView is the new control introduced in v7 Support Library. Its a FrameLayout with shadow,
   corner radius and elevation property after Android Lollipop.
   CardView is mostly used for good looking UI with RecyclerView.

*  RecyclerView : (```androidx.recyclerview:recyclerview:1.1.0```)

   Description:
   Android RecyclerView is the advanced version of ListView for more improvements and features.
   You can create an attached list with ListView but with RecyclerView, you can do more with
   recyclerview:
   1. Reuses cells while scrolling
   2. Animate to list items
   3. Change the item container ( linear layout or grid layout using Layout Manager)
   4. Divider between two items
   5. Multiple views in the same listing
   
*  Custom LOG Class is used.
   This class will make sure that all the logs are disabled in the release build. 
   ```
    fun e(tag: String?, msg: String) {
        if (!BuildConfig.ON_DEBUG_MODE) return
        if (msg.length > 4000) {
            Log.e(tag, msg.substring(0, 4000))
            e("", "" + checkInstanceOfException(msg.substring(4000)))
        } else Log.e(tag, "" + checkInstanceOfException(msg))
    }
    ```
    
    ```
         buildConfigField "boolean", "ON_DEBUG_MODE", "true"

        /**
         * We are using "ON_DEBUG_MODE", in AppLogger Class
         */

        /**
         * In Debug build it is "true", so the Log will be printed.
         */
    ```

# HOW TO MAE A RELEASE APK:

To make a release apk, you have to change the location of the keystore as per your location.
Password is written in gradle file.
