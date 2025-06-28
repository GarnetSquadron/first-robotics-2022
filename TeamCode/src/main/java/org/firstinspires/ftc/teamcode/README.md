hi 

here are some of the most important classes in this code


[Bot.java](Subsystems%2FBot.java)
    Holds all imports and objects used to import methods. Basically, when you are making a
    new opmode, instead of creating a new instance of each object you need, simply create
    an instance of this and

[MecanumDrive.java](roadrunner%2FMecanumDrive.java)
    the mecanum drive code, implements roadrunner

[TeleOpActionScheduler.java](OpmodeActionSceduling%2FTeleOpActionScheduler.java)
    Adds action supports for non autonomous

[TeleOpAction.java](OpmodeActionSceduling%2FTeleOpAction.java)
    Used by teleop action scheduler, hotels information

DC Motor Ex: 
    Another team’s motor class, used only in mecanum drive

Pinpoint Drive: 
    All of the roadrunner drive code

Headless Drive Command: 
    Takes pinpoint drive as a constructor input, headless drive code

Outtake and Intake (Robot system code): 
    Specific functions to use robot systems

Intake: 
    Consolidates multiple smaller subsystems that the intake uses, passing hardware map, creating actions

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
    