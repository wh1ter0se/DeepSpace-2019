// NeoPixel Ring simple sketch (c) 2013 Shae Erisson
// released under the GPLv3 license to match the rest of the AdaFruit NeoPixel library

#include <Adafruit_NeoPixel.h>
#ifdef __AVR__
  #include <avr/power.h>
#endif

// Which pin on the Arduino is connected to the NeoPixels?
// On a Trinket or Gemma we suggest changing this to 1
#define PIN            6

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

//  opposingColors();
  getCapitalismed();
//  blueFire();
//    delay(random(50,100)); // Delay for a period of time (in milliseconds). 
  
}

void redFire() {
  for(int i=0;i< NUMPIXELS;i++){
    // pixels.Color takes RGB values, from 0,0,0 up to 255,255,255
    int flicker= random(1, 15);
    int r = 255 - flicker * 10;
    int g = flicker;
    int b = 0;
    int w = flicker;
    pixels.setPixelColor(i, pixels.Color(g, r, b, w)); 
  }
    pixels.show(); // This sends the updated pixel color to the hardware.
}

void blueFire() {
  for(int i=0;i< NUMPIXELS;i++){
    // pixels.Color takes RGB values, from 0,0,0 up to 255,255,255
    int flicker= random(1, 15);
    int r = 0;
    int g = r / 32;
    int b = 255 - flicker * 16;
    int w = flicker;
    pixels.setPixelColor(i, pixels.Color(g, r, b, w)); 
  }
    pixels.show(); // This sends the updated pixel color to the hardware.
}

void getCapitalismed() {
  for(int i=0;i<(NUMPIXELS/3);i++) {
    pixels.setPixelColor(i, pixels.Color(0,0,255,0));
  }
//  for(int i=1;i<(NUMPIXELS/3);i+=2) {
//    pixels.setPixelColor(i, pixels.Color(0,0,0,255));
//  }
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
