hi

### IF YOU ARE A BEGINNER(OR REALLY ANYONE WHO HASNT SEEN THIS ) THIS WILL SAVE YOU SO MUCH PAIN:

[The_Unintuitive_Rules_Of_Servos.md](..%2F..%2F..%2F..%2FThe_Unintuitive_Rules_Of_Servos.md)

### here are some of the most important classes in this code

[Bot](Subsystems/Bot.java)
Holds all imports and objects used to import methods. Basically, when you are making a
new opmode, instead of creating a new instance of each object you need, simply create
an instance of this and

[MecanumDrive](roadrunner/drives/MecanumDrive.java)
the mecanum drive code, implements roadrunner

[TeleOpActionScheduler](OpmodeActionSceduling/TeleOpActionScheduler.java)
Adds action supports for non autonomous

[TeleOpAction](OpmodeActionSceduling/TeleOpAction.java)
Used by teleop action scheduler, hotels information

[MotorEx](com/arcrobotics/ftclib/hardware/motors/MotorEx.java)
ftc lib’s motor class, used only in mecanum drive

[PinpointLocalizer](roadrunner/localizers/PinpointLocalizer.java)
a class that roadrunnerifies the pinpoint
Headless Drive Command:
Takes mecanum drive as a constructor input, headless drive code

Outtake and Intake (Robot system code):
Specific functions to use robot systems

Intake:
Consolidates multiple smaller subsystems that the intake uses, passing hardware map, creating
actions

ActionServo:
Action compatible servo class

CRServo:
continuous rotation servo

Servo:
servo class provided by sdk

REMOVE EXTENDS SUBSYSTEM BASE

DELETE REGULAR DRIVE

Motor:
SDK Motor class
MOTOR:
Custom motor class with controller support

RAWMOTOR:
Basic custom motor class with max power and encoder support

UpdatableMOTOR:
Uses UpdatePower in a loop to continuously update power

ACTIONMOTOR:
a motor class with actions

LimitedMotor:
A motor with limited position

UpdatePower:
Updates power

DistanceSensorMotor:
Limited motor with distance sensor report

### USEFULLLINKS:

intro to ftc sdk:

https://ftc-docs.firstinspires.org/en/latest/ftc_sdk/overview/index.html

https://ftc-docs.firstinspires.org/en/latest/programming_resources/android_studio_java/Android-Studio-Tutorial.html

roadrunner docs:

https://rr.brott.dev/docs/v1-0