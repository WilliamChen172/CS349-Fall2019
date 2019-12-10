# CS349 A5
Student: w279chen
Marker: Rebecca Santos


Total: 98 / 100 (98.00%)

Code: 
(CO: won’t compile, CR: crashes, FR: UI freezes/unresponsive, NS: not submitted)


Notes:   

## I. Deliverables (5%)

1. [5/5] Code compiles and runs, and includes an APK for grading. README is included.

## Basic Functionality (15%)

2. [5/5] The toolbar or menu bar contains a "Reset" functionality tom reset the position of the paper doll.

3. [5/5] The toolbar or menu bar contains "About" functionality to display details.

4. [5/5] A paper doll is visible, and is assembled in the rough shape of the doll.

## Translation (15%)

5. [5/5] The paper doll's torso is the only body part that can be translated.

6. [5/5] The paper doll can be translated with direct manipulation on the torso with movements that match the touch input one-to-one.

7. [5/5] When the paper doll is translated by the torso, the child elements of the torso are translated simultaneously.

## Rotation (20%)

8. [5/5] All body parts of the paper doll can be rotated (4), except for the torso, which does not rotate (1).

9. [5/5] The paper doll's body parts can be rotated with direct manipulation on the body part (3), with rotations that match the touch event about the pivot one-to-one.

10.  [5/5] When the paper doll's body parts are rotated, the child elements of the body part are rotated simultaneously.

11. [5/5] The appropriate body parts have limited rotation, relative to the rotation of their parent element.

## Scaling (20%)

12.  [5/5] The legs of the paper doll are the only body part that can be scaled.

13.  [5/5] The legs can be scaled with direct manipulation, with scaling that matches the distance between the user's two touch events used for scaling.

14. [5/5] When the upper legs are scaled, the lower legs are scaled as well, but the feet are not scaled.

15. [5/5] The legs scale along their primary axis, even when the lower leg has been rotated relative to the upper leg.

## Robustness (15%)

16. [5/5] Grabbing a body part for direct manipulation does not cause the body part to change at all until the mouse begins to move.

17.  [5/5] The movement of body parts is smooth while they're being directly manipulated within constraints. (i.e. The body part does not "snap" or "pop" in intervals.)

18. [3/5] When a body part is manipulated, the child elements of the body part are also updated smoothly.

-2 Feet do not translate correctly when legs scale

## Chosen Feature (10%)

19. [10/10] One of the following options is implemented:

    #2 - Multiple Dolls: There is a second menu (2) containing a list of at least one additional paper doll (2), including the initial human-shaped paper doll (1). The additional paper dolls are not human-shaped (5) and have their own rotation constraints (5).
