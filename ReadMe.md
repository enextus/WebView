Image Processor

Image Processor is a user-friendly application that allows users to easily convert color images into black and white versions using different weight combinations for the color channels. The application provides a graphical user interface to select an input image, preview the converted images, and save the desired result.
Features

    Select a color image in common formats (e.g., JPEG, PNG)
    Convert the image into three black and white versions using different weight combinations
    Preview the original color image and the converted black and white images side by side
    Save the desired black and white image as a new file in JPEG format

Prerequisites

    Java 20 or later
    Maven for building the project

How to Build

    Clone the repository to your local machine:

bash

git clone https://github.com/yourusername/image-processor.git

    Navigate to the project directory:

arduino

cd image-processor

    Build the project using Maven:

go

mvn clean package

How to Run

After building the project, navigate to the target directory and run the following command:

arduino

java -jar image-processor-1.0-SNAPSHOT.jar

This will launch the Image Processor application. Select a color image to process, preview the results, and save the desired black and white image.
License

This project is licensed under the MIT License. See the LICENSE file for more information.