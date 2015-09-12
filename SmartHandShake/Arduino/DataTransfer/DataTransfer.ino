#include <TAH.h>

TAH myTAH;
long randNumber;

void setup()  
{
 
   Serial.begin(9600);                       // Open serial port
  myTAH.begin(9600);                        // Start TAH ble serial port
  
  myTAH.enterCommandMode();                 // Enters TAH command mode
 myTAH.setName("Blackpearl");
 myTAH.setWorkRole(SLAVE);
 myTAH.setAuth(OPEN);
 myTAH.setiBeaconMode(ON);
  // initialize digital pin 13 as an output.
  pinMode(13, OUTPUT);
  randomSeed(analogRead(0));
myTAH.exitCommandMode();                  // Saves changed settings and exit command mode
  


  
}

void loop() // run over and over
{

  randNumber = random(5);
  if(randNumber == 0) {
  myTAH.setiBeaconUUIDstring1("aa");
  } else if (randNumber == 1) {
  myTAH.setiBeaconUUIDstring1("bb");
    } else if (randNumber == 2) {
  myTAH.setiBeaconUUIDstring1("cc");
    } else if (randNumber == 3) {
  myTAH.setiBeaconUUIDstring1("dd");
    } else if (randNumber == 4) {
  myTAH.setiBeaconUUIDstring1("dd");
    } else {
      myTAH.setiBeaconUUIDstring1("ff");
    }
  digitalWrite(13, HIGH);   // turn the LED on (HIGH is the voltage level)
  delay(500);              // wait for a second/
  digitalWrite(13, LOW);    // turn the LED off by making the voltage LOW
  delay(500);  




  
}

