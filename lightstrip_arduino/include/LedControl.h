#include <Arduino.h>
#include <FastLED.h>

void LED_init(void);
void LED_changeColor(uint8_t r, uint8_t g, uint8_t b, uint8_t brightness);
void LED_ledsOn(void);
void LED_ledsOff(void);
void LED_debugErrFlash(uint8_t timesToFlash, uint8_t delayMs, CRGB color);