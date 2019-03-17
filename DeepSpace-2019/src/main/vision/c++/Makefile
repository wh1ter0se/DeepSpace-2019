#I do the big compile
CXX = g++
CV = `pkg-config --cflags --libs opencv`

#files: Main.cpp, PairData.cpp, PostProcessor.cpp Settings.cpp, Settings.h, UDP.cpp Util.cpp Vision.h 

bin/Main.o: Main.cpp
	$(CXX) -c -o bin/Main.o Main.cpp $(CV)

bin/PairData.o: PairData.cpp
	$(CXX) -c -o bin/PairData.o PairData.cpp $(CV)
	
bin/RightRect.o: RightRect.cpp
	$(CXX) -c -o bin/RightRect.o RightRect.cpp $(CV)

bin/PostProcessor.o: PostProcessor.cpp
	$(CXX) -c -o bin/PostProcessor.o PostProcessor.cpp $(CV)

bin/Settings.o: Settings.cpp
	$(CXX) -c -o bin/Settings.o Settings.cpp $(CV)

bin/Settings_h.o: Settings.h
	$(CXX) -c -x c++ -o bin/Settings_h.o Settings.h $(CV)

bin/UDP.o: UDP.cpp
	$(CXX) -c -o bin/UDP.o UDP.cpp $(CV)

bin/Util.o: Util.cpp
	$(CXX) -c -o bin/Util.o Util.cpp $(CV)

bin/Vision_h.o: Vision.h
	$(CXX) -c -x c++ -o bin/Vision_h.o Vision.h $(CV)

Vision: bin/Main.o bin/PairData.o bin/RightRect.o bin/PostProcessor.o bin/Settings.o bin/Settings_h.o bin/UDP.o bin/Util.o bin/Vision_h.o
	$(CXX) -o Vision bin/Main.o bin/PairData.o bin/RightRect.o bin/PostProcessor.o bin/Settings.o bin/Settings_h.o bin/UDP.o bin/Util.o bin/Vision_h.o $(CV)
