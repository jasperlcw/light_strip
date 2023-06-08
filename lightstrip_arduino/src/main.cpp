#include <Arduino.h>
#include <FastLED.h>
#include "LedControl.h"

const char* CONNECT_STRING = "Hello from Arduino!";
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
   if (Serial.available()) { // Bug potentially here for reading serial data
      uint8_t bytesRead = Serial.readBytes(cmd, CMD_BUFFER_SIZE);

      if (bytesRead != CMD_BUFFER_SIZE) {
         Serial.print("Client sent ");
         Serial.print(bytesRead);
         Serial.println(" bytes instead of the expected 5.");

         LED_debugErrFlash(bytesRead, 300, CRGB(CRGB::HTMLColorCode::Yellow));

         // flush the buffer
         while (Serial.available()) {
            Serial.read();
         }
      }
      else {
         LED_debugErrFlash(bytesRead, 300, CRGB(CRGB::HTMLColorCode::Green));

         switch (cmd[0]) {
            case CHANGE_COLOR:
               // client sends values in r, g, b, brightness
               LED_changeColor(cmd[1], cmd[2], cmd[3], cmd[4]);
               break;
            case LED_ON:
               LED_ledsOn();
               break;
            case LED_OFF:
               LED_ledsOff();
               break;
            default:
               LED_debugErrFlash(2, 300, CRGB(CRGB::HTMLColorCode::Red));
               break;
         }
      }
   }
}
