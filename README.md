# Digit-Recognizer-using-Tflite 

A Hand written digit recognizer on android devices using Tensorflow Lite. I trained a neural network using Tensorflow Keras and saved its weights. Using the TFLite converter API, I converted the HDF5 file to tflite format. For the android part, I implemented a basic Canvas to draw the single digit and then created a interface to interact with the tflite model to get the results.

You can check out the kaggle notebook for understanding the model: [Notebook](https://www.kaggle.com/rahuldshetty/mnist-hand-written-digit-classification)

Install the App on your android device by following this link: [APK](https://github.com/rahuldshetty/Digit-Recognizer-using-Tflite/raw/master/app-debug.apk)

![Screenshots](https://raw.githubusercontent.com/rahuldshetty/Digit-Recognizer-using-Tflite/master/output_rt35wm.gif)

# References

Google's tutorial on this project(uses Kotlin): [Codelabs](https://codelabs.developers.google.com/codelabs/digit-classifier-tflite/#0)

Some part of code for preprocessing and interacting with the model are taken from here: [tflite-mnist-android](https://github.com/nex3z/tflite-mnist-android) and [Medium Article](https://medium.com/tensorflow/using-tensorflow-lite-on-android-9bbc9cb7d69d)

