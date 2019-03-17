
CXX = g++
CV = `pkg-config --cflags --libs opencv`
GTK = `pkg-config --cflags --libs gtk+-2.0`


bin/Main.o: Main.cpp
	$(CXX) -c -o bin/Main.o Main.cpp $(CV) $(GTK)

bin/Calibration_h.o: Calibration.h
	$(CXX) -c -x c++ -o bin/Calibration_h.o Calibration.h $(CV) $(GTK)

bin/Calibration.o: Calibration.cpp
	$(CXX) -c -o bin/Calibration.o Calibration.cpp $(CV) $(GTK)

bin/Tuning.o: Tuning.cpp
	$(CXX) -c -o bin/Tuning.o Tuning.cpp $(CV) $(GTK)

bin/PostProcessor.o: PostProcessor.cpp
	$(CXX) -c -o bin/PostProcessor.o PostProcessor.cpp $(CV) $(GTK)

Calibrate: bin/Main.o bin/Calibration_h.o bin/Calibration.o bin/Tuning.o bin/PostProcessor.o
	$(CXX) -o Calibrate bin/Main.o bin/Calibration_h.o bin/Calibration.o bin/Tuning.o bin/PostProcessor.o $(CV) $(GTK)