#just a file that compiles and starts the program for use as I learn how to do makefiles

echo "Compiling Project..."
g++ -o Main Main.cpp Vision.h PostProcessor.cpp Util.cpp Settings.h Settings.cpp`pkg-config --cflags --libs opencv`
echo "Executing"
./Main
