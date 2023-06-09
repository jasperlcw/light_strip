#include <Arduino.h>
#include <FastLED.h>
#include "LedControl.h"

const char* CONNECT_STRING = "Hello from Arduino!";

// 1 byte for the command, 6 bytes for the hex code, last 3 spaces unused
// 1 byte for command, then 8 bytes for a long to contain hex code
const uint8_t CMD_BUFFER_SIZE = 5;
uint8_t cmd[CMD_BUFFER_SIZE];

// Commands
const uint8_t CHANGE_COLOR = 0;
const uint8_t LED_ON = 1;
const uint8_t LED_OFF = 2;

void setup()
{
   memset(cmd, 0, CMD_BUFFER_SIZE);
   LED_init();
   Serial.begin(9600);
   delay(200);
   Serial.print(CONNECT_STRING);
}

void loop() 
{
   if (Serial.available()) {
      Serial.readBytes(cmd, CMD_BUFFER_SIZE);

      switch (cmd[0]) {
         case CHANGE_COLOR:
            // LED_debugFlash(bytesRead, 300, CRGB::HTMLColorCode::Green);
            // client sends values in r, g, b, brightness
            LED_changeColor(cmd[1], cmd[2], cmd[3], cmd[4]);
            break;
         case LED_ON:
            // LED_debugFlash(bytesRead, 300, CRGB::HTMLColorCode::Blue);
            LED_ledsOn();
            break;
         case LED_OFF:
            // LED_debugFlash(bytesRead, 300, CRGB::HTMLColorCode::Yellow);
            LED_ledsOff();
            break;
         default:
            LED_debugFlash(2, 300, CRGB::HTMLColorCode::Orange);
            break;
      }
   }
}
