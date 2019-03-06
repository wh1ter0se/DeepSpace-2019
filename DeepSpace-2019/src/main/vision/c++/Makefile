#I do the big compile
CXX = g++
CV = `pkg-config --cflags --libs opencv`

#files: Main.cpp, PairData.cpp, PostProcessor.cpp Settings.cpp, Settings.h, UDP.cpp Util.cpp Vision.h 

Main.o: Main.cpp
	$(CXX) -c -o Main.o Main.cpp $(CV)

PairData.o: PairData.cpp
	$(CXX) -c -o PairData.o PairData.cpp $(CV)
	
RightRect.o: RightRect.cpp
	$(CXX) -c -o RightRect.o RightRect.cpp $(CV)

PostProcessor.o: PostProcessor.cpp
	$(CXX) -c -o PostProcessor.o PostProcessor.cpp $(CV)

Settings.o: Settings.cpp
	$(CXX) -c -o Settings.o Settings.cpp $(CV)

Settings_h.o: Settings.h
	$(CXX) -c -x c++ -o Settings_h.o Settings.h $(CV)

UDP.o: UDP.cpp
	$(CXX) -c -o UDP.o UDP.cpp $(CV)

Util.o: Util.cpp
	$(CXX) -c -o Util.o Util.cpp $(CV)

Vision_h.o: Vision.h
	$(CXX) -c -x c++ -o Vision_h.o Vision.h $(CV)

Vision: Main.o PairData.o RightRect.o PostProcessor.o Settings.o Settings_h.o UDP.o Util.o Vision_h.o
	$(CXX) -o Vision Main.o PairData.o RightRect.o PostProcessor.o Settings.o Settings_h.o UDP.o Util.o Vision_h.o $(CV)
