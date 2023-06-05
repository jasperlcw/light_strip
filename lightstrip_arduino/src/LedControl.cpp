#include <Arduino.h>
#include <FastLED.h>

#include "LedControl.h"

const uint8_t NUM_LEDS = 29;
const uint8_t DATA_PIN = 2;
const uint8_t CALIBRATE_LEDS = 7;

static CRGB leds[NUM_LEDS];
static uint8_t r, g, b, brightness;

static void updateAllLeds(uint8_t newR, uint8_t newG, uint8_t newB);
static void updateAllLeds(CRGB newColor);

void LED_init(void)
{
   // Current lightstrip uses GRB ordering
   FastLED.addLeds<WS2812, DATA_PIN, GRB>(leds, NUM_LEDS);
   for (int i = 0; i < NUM_LEDS; i++) {
      leds[i] = CRGB(CRGB::HTMLColorCode::White);
   }
   FastLED.setBrightness(10);
   FastLED.show();
}

void LED_changeColor(uint8_t newR, uint8_t newG, uint8_t newB, uint8_t newBrightness)
{
   FastLED.clear();
   updateAllLeds(newR, newG, newB);
   FastLED.setBrightness(newBrightness);
   FastLED.show();
   r = newR;
   g = newG;
   b = newB;
   brightness = newBrightness;
}

void LED_ledsOn(void)
{
   FastLED.setBrightness(brightness);
   FastLED.show();
}

void LED_ledsOff(void)
{
   FastLED.setBrightness(0);
   FastLED.show();
}

void LED_debugErrFlash(uint8_t timesToFlash, uint8_t delayMs, CRGB color)
{
   for (uint8_t i = 0; i < timesToFlash; i++) {
      FastLED.clear();
      // updateAllLeds(255, 0, 0);
      updateAllLeds(color);
      FastLED.show();
      delay(delayMs);

      FastLED.clear();
      // updateAllLeds(0, 0, 0);
      updateAllLeds(CRGB(CRGB::HTMLColorCode::Black));
      FastLED.show();
      delay(delayMs);
   }

   FastLED.clear();
   updateAllLeds(r, g, b);
   FastLED.show();
}

static void updateAllLeds(uint8_t newR, uint8_t newG, uint8_t newB)
{
   for (int i = 0; i < NUM_LEDS; i++) {
      leds[i] = CRGB(newR, newG, newB);
   }
}

static void updateAllLeds(CRGB newColor)
{
   for (int i = 0; i < NUM_LEDS; i++) {
      leds[i] = newColor;
   }
}
