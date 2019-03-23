// NeoPixel Ring simple sketch (c) 2013 Shae Erisson
// released under the GPLv3 license to match the rest of the AdaFruit NeoPixel library

#include <Adafruit_NeoPixel.h>
#ifdef __AVR__
  #include <avr/power.h>
#endif

// Which pin on the Arduino is connected to the NeoPixels?
// On a Trinket or Gemma we suggest changing this to 1
#define PIN            6

#define THREE          26
#define SIX            32
#define NINE           38
#define FIVE           44
#define LOOP           50

// How many NeoPixels are attached to the Arduino?
#define NUMPIXELS      120
// When we setup the NeoPixel library, we tell it how many pixels, and which pin to use to send signals.
// Note that for older NeoPixel strips you might need to change the third parameter--see the strandtest
// example for more information on possible values.
Adafruit_NeoPixel pixels = Adafruit_NeoPixel(NUMPIXELS, PIN, NEO_RGBW + NEO_KHZ800);

void setup() {
  pixels.begin(); // This initializes the NeoPixel library.
}

void loop() {
//rainbowCycle(0);
//  opposingColors();
//  getCapitalismed();
//redFire();
  blueFire();
//  redNumbers();
//  capitalistNumbers();
//nice();
}

void redFire() {
  for(int i=0;i< NUMPIXELS;i++){
    // pixels.Color takes RGB values, from 0,0,0 up to 255,255,255
    int flicker= random(1, 15);
    int r = 255 - flicker * 16;
    int g = flicker;
    int b = 0;
    int w = flicker / 2;
    pixels.setPixelColor(i, pixels.Color(g, r, b, w)); 
  }
    pixels.show(); // This sends the updated pixel color to the hardware.
    delay(random(50,100)); // Delay for a period of time (in milliseconds). 
}

void blueFire() {
  for(int i=0;i< NUMPIXELS;i++){
    // pixels.Color takes RGB values, from 0,0,0 up to 255,255,255
    int flicker= random(1, 15);
    int r = flicker;
    int g = r / 32;
    int b = 255 - flicker * 24;
    int w = flicker;
    pixels.setPixelColor(i, pixels.Color(g, r, b, w)); 
  }
    pixels.show(); // This sends the updated pixel color to the hardware.
    delay(random(50,100)); // Delay for a period of time (in milliseconds). 
}

void getCapitalismed() {
  for(int i=0;i<(NUMPIXELS/3);i++) {
    pixels.setPixelColor(i, pixels.Color(0,0,255,0));
  }
  for (int i=(NUMPIXELS/3);i<NUMPIXELS;i+=2) {
    pixels.setPixelColor(i, pixels.Color(0,255,0,0));
  }
  for (int i=(NUMPIXELS/3)+1;i<NUMPIXELS;i+=2) {
    pixels.setPixelColor(i, pixels.Color(0,0,0,255));
  }
  pixels.show();
  delay(200);
  for(int i=0;i<(NUMPIXELS/3);i++) {
    pixels.setPixelColor(i, pixels.Color(0,0,255,0));
  }
//  for(int i=1;i<(NUMPIXELS/3);i+=2) {
//    pixels.setPixelColor(i, pixels.Color(0,0,0,255));
//  }
  for (int i=(NUMPIXELS/3);i<NUMPIXELS;i+=2) {
    pixels.setPixelColor(i, pixels.Color(0,0,0,255));
  }
  for (int i=(NUMPIXELS/3)+1;i<NUMPIXELS;i+=2) {
    pixels.setPixelColor(i, pixels.Color(0,255,0,0));
  }
  pixels.show();
  delay(200);
}

void opposingBrickLoader() {
  for (int i=0;i<NUMPIXELS/2;i++) {
    for(int n=0;n<i;n++) {
      pixels.setPixelColor(n, pixels.Color(0,0,0,0));
    }
    for(int j=i;j<NUMPIXELS/2;j++) {
      for(int k=i;k<NUMPIXELS/2;k++) {
        pixels.setPixelColor(k, pixels.Color(0,0,0,0));
      }
      pixels.setPixelColor(j, pixels.Color(0,0,0,255));
    }
    pixels.show();
    delay(10);
  }
}

void opposingColors() {
  for (int i=(NUMPIXELS/2);i>0;i--) {
    for (int j=0;j<i;j++) {
      pixels.setPixelColor(j, pixels.Color(0,255,0,0));
    }
    for (int j=i;j<(NUMPIXELS/2);j++) {
      pixels.setPixelColor(j, pixels.Color(0,0,255,0));
    }
    pixels.show();
    delay(10);
  }
}

void opposingColors2() {
  for (int i=0;i<(NUMPIXELS/2);i++) {
    for (int j=0;j<NUMPIXELS;j++) {
      pixels.setPixelColor(j, pixels.Color(0,255,0,0));
    }
    for (int j=i;j<(NUMPIXELS-i);j++) {
      pixels.setPixelColor(j, pixels.Color(0,0,255,0));
    }
    pixels.show();
    delay(10);
  }
  for (int i=0;i<(NUMPIXELS/2);i++) {
    for (int j=0;j<NUMPIXELS;j++) {
      pixels.setPixelColor(j, pixels.Color(0,0,255,0));
    }
    for (int j=i;j<(NUMPIXELS-i);j++) {
      pixels.setPixelColor(j, pixels.Color(0,255,0,0));
    }
    pixels.show();
    delay(10);
  }
}


// Slightly different, this makes the rainbow equally distributed throughout
void rainbowCycle(uint8_t wait) {
  uint16_t i, j;

  for(j=0; j<256*5; j++) { // 5 cycles of all colors on wheel
    for(i=0; i< pixels.numPixels(); i++) {
      pixels.setPixelColor(i, Wheel(((i * 256 / pixels.numPixels()) + j) & 255));
    }
    pixels.show();
    delay(wait);
  }
}

// Input a value 0 to 255 to get a color value.
// The colours are a transition r - g - b - back to r.
uint32_t Wheel(byte WheelPos) {
  if(WheelPos < 85) {
   return pixels.Color(WheelPos * 3, 255 - WheelPos * 3, 0, 255);
  } else if(WheelPos < 170) {
   WheelPos -= 85;
   return pixels.Color(255 - WheelPos * 3, 0, WheelPos * 3, 255);
  } else {
   WheelPos -= 170;
   return pixels.Color(0, WheelPos * 3, 255 - WheelPos * 3, 255);
  }
}

void redFireOnLoop() {
  for(int i=LOOP;i< NUMPIXELS;i++){
    // pixels.Color takes RGB values, from 0,0,0 up to 255,255,255
    int flicker= random(1, 15);
    int r = 255 - flicker * 10;
    int g = flicker;
    int b = 0;
    int w = flicker;
    pixels.setPixelColor(i, pixels.Color(g, r, b, w)); 
  }
    pixels.show(); // This sends the updated pixel color to the hardware.
    // delay(random(50,100)); // Delay for a period of time (in milliseconds). 
}

void redNumbers() {
  int wait = 500;
  for (int i=0;i<NUMPIXELS;i++) {
    pixels.setPixelColor(i, pixels.Color(0,0,0,0));
  }
  pixels.show();
  delay(wait);
  for (int i=THREE;i<SIX;i++) {
    pixels.setPixelColor(i, pixels.Color(0,255,0,0));
  }
  pixels.show();
  delay(wait);
  pixels.show();
  for (int i=SIX;i<NINE;i++) {
    pixels.setPixelColor(i, pixels.Color(0,255,0,0));
  }
  pixels.show();
  delay(wait);
  pixels.show();
  for (int i=NINE;i<FIVE;i++) {
    pixels.setPixelColor(i, pixels.Color(0,255,0,0));
  }
  pixels.show();
  delay(wait);
  pixels.show();
  for (int i=FIVE;i<LOOP;i++) {
    pixels.setPixelColor(i, pixels.Color(0,255,0,0));
  }
  pixels.show();
  delay(wait);
}

void blueNumbers() {
  int wait = 500;
  for (int i=0;i<NUMPIXELS;i++) {
    pixels.setPixelColor(i, pixels.Color(0,0,0,0));
  }
  pixels.show();
  delay(wait);
  for (int i=THREE;i<SIX;i++) {
    pixels.setPixelColor(i, pixels.Color(0,0,255,0));
  }
  pixels.show();
  delay(wait);
  pixels.show();
  for (int i=SIX;i<NINE;i++) {
    pixels.setPixelColor(i, pixels.Color(0,0,255,0));
  }
  pixels.show();
  delay(wait);
  pixels.show();
  for (int i=NINE;i<FIVE;i++) {
    pixels.setPixelColor(i, pixels.Color(0,0,255,0));
  }
  pixels.show();
  delay(wait);
  pixels.show();
  for (int i=FIVE;i<LOOP;i++) {
    pixels.setPixelColor(i, pixels.Color(0,0,255,0));
  }
  pixels.show();
  delay(wait);
}

void nice() {
  for(int i=0;i<NUMPIXELS;i++) {
    pixels.setPixelColor(i, pixels.Color(0,0,0,0));
  }
  for (int i=SIX;i<FIVE;i++) {
    pixels.setPixelColor(i, pixels.Color(0,255,0,0));
  }
  pixels.show();
}

void capitalistNumbers() {
  for(int i=THREE;i<NINE;i++) {
    pixels.setPixelColor(i, pixels.Color(0,0,255,0));
  }
  for (int i=NINE;i<FIVE;i++) {
    pixels.setPixelColor(i, pixels.Color(0,255,0,0));
  }
  for (int i=FIVE;i<LOOP;i++) {
    pixels.setPixelColor(i, pixels.Color(0,0,0,255));
  }
  pixels.show();
  delay(200);
  for(int i=THREE;i<NINE;i++) {
    pixels.setPixelColor(i, pixels.Color(0,0,255,0));
  }
//  for(int i=1;i<(NUMPIXELS/3);i+=2) {
//    pixels.setPixelColor(i, pixels.Color(0,0,0,255));
//  }
  for (int i=NINE;i<FIVE;i++) {
    pixels.setPixelColor(i, pixels.Color(0,0,0,255));
  }
  for (int i=FIVE;i<LOOP;i++) {
    pixels.setPixelColor(i, pixels.Color(0,255,0,0));
  }
  pixels.show();
  delay(200);
}

