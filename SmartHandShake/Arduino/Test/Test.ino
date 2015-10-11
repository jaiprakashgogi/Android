#include <TAH.h>

TAH myTAH;

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
  
myTAH.exitCommandMode();                  // Saves changed settings and exit command mode
  


  
}

void loop() // run over and over
{
  
  /*if (myTAH.available())
  {
    //Serial.write(myTAH.read());
    Serial.print('10');
    
  }
  
  
  if (Serial.available())
  {

        myTAH.write(Serial.read());
  }*/
  myTAH.setiBeaconUUIDstring1("aa");
  myTAH.setiBeaconUUIDstring2("bb");
  myTAH.setiBeaconUUIDstring3("cc");
  myTAH.setiBeaconUUIDstring4("dd");
  digitalWrite(13, HIGH);   // turn the LED on (HIGH is the voltage level)
  delay(1000);              // wait for a second/
  myTAH.setiBeaconUUIDstring1("ee");
  myTAH.setiBeaconUUIDstring2("ff");
  myTAH.setiBeaconUUIDstring3("gg");
  myTAH.setiBeaconUUIDstring4("hh");
  digitalWrite(13, LOW);    // turn the LED off by making the voltage LOW
  delay(1000);  




  
}

