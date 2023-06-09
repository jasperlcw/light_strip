#include <Arduino.h>
#include <EEPROM.h>
#include "LedStorage.h"

// EEPROM cell positions 1-3 will be ordered as RGB 
// and position 4 will store brightness value.

#define RED_CELL 0
#define GREEN_CELL 1
#define BLUE_CELL 2
#define BRIGHTNESS_CELL 3

void Storage_updateEepromRed(uint8_t newR)
{
   EEPROM.update(RED_CELL, newR);
}

void Storage_updateEepromGreen(uint8_t newG)
{
   EEPROM.update(GREEN_CELL, newG);
}

void Storage_updateEepromBlue(uint8_t newB)
{
   EEPROM.update(BLUE_CELL, newB);
}

void Storage_updateEepromBrightness(uint8_t newBrightness)
{
   EEPROM.update(BRIGHTNESS_CELL, newBrightness);
}

uint8_t Storage_getEepromRed(void)
{
   return EEPROM.read(RED_CELL);
}

uint8_t Storage_getEepromGreen(void)
{
   return EEPROM.read(GREEN_CELL);
}

uint8_t Storage_getEepromBlue(void)
{
   return EEPROM.read(BLUE_CELL);
}

uint8_t Storage_getEepromBrightness(void)
{
   return EEPROM.read(BRIGHTNESS_CELL);
}
