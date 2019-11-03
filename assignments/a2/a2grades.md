# CS349 A2
Student: w279chen
Marker: Bahar Sarrafzadeh


Total: 88 / 100 (88.00%)

Code: 
(CO: won’t compile, CR: crashes, FR: UI freezes/unresponsive, NS: not submitted)


Notes:   

## Deliverables (5%)

1. [5/5] Appropriate project files and readme.txt. Code compiles and runs with make file.

## Interface displays, and includes required functionality. (15%)

2. [5/5] The application consists of a single screen with an image container and a main toolbar.

3. [5/5] There are clear mechanisms to clear the images, load a set of images, and filter images. These can appear on the main toolbar or a secondary toolbar.

4. [5/5] The "Ratings" widget appears in the grid below each image.

## Grid and List layouts supported. (25%)

5. [10/10] Grid Layout shows a fixed number of columns (2 or more). No horizontal bar should appear.

6. [10/10] List Layout shows a single column. No horizontal bar should appear.

7. [5/5] Layout can be toggled between grid and list layouts with two buttons

## Load and view images and metadata. (20%)

8. [3/10] The "Load Images" button should clear the image layout and ratings. It should also load the appropriate images and display them.

-5  There is a problem loading the images on the A2 site: new images are loaded with some already assigned ratings. These ratings are not even the same as what was assigned before the previous images were cleared and the new set is loaded. 
-2  The filter setting is not cleared before the new images are loaded.

9. [5/5] The application should support loading and displaying multiple formats, including JPG and PNG.

10. [5/5] Clicking on an image shows an enlarged image in a separate window with the rating bar. There is a way of closing the window.

## Rank images, filter based on ranking. (20%)

11. [5/5] Individual images can be ranked by changing the rating for the image.

12. [10/10] The ratings filter on the toolbar filters out the images that don't meet the specified rating or higher.

13. [0/5] Changing the rating of an image should immediately be reflected in the list (i.e. if it no longer meets the filter criteria, it should disappear).

-5 Images do not disappear automatically when they do not meet the filter criteria.		

## Resizing handled appropriately based on layout. (15%)

14. [8/8] When resizing the window in Grid Layout, space is constrained / allocated correctly.

15. [7/7] When resizing the window in List Layout, space is constrained / allocated correctly.
