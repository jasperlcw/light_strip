/* Author: Jasper Wong
*
*  Module to provide access to the integrated EEPROM for setting and retrieving
*  RGB and brightness values for persistent storage.
*/
#pragma once
#include <Arduino.h>

void Storage_updateEepromRed(uint8_t newR);
void Storage_updateEepromGreen(uint8_t newG);
void Storage_updateEepromBlue(uint8_t newB);
void Storage_updateEepromBrightness(uint8_t newBrightness);
uint8_t Storage_getEepromRed(void);
uint8_t Storage_getEepromGreen(void);
uint8_t Storage_getEepromBlue(void);
uint8_t Storage_getEepromBrightness(void);
