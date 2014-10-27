Gravitas
========

Add Gravitas to your User Interface!

This is the raw Java code for the simple physics engine that <a href="https://play.google.com/store/apps/details?id=com.infinite_imagination.letterplex">Letterplex</a> was built with. "Soon" I'll add some examples of integrating it into Android apps, and controling physics systems with touch.

These are the physics models implemented here:

*  Spring: a damped spring. It takes the standard mass, spring constant and damping numbers.
*  Friction: models deceleration by friction. It's used to implement (some of) scrolling in Letterplex.
*  Gravity: models acceleration or deceleration from a constant force (such as gravity). This is the `F = ma` equation you learned in high school.
*  Scroll: combines Spring and Friction to implement scrolling with overbounce.
*  Fall: combines Gravity with a Spring. The value falls until it reaches a threshold (the ground, perhaps) where it bounces (its momentum is rolled into the spring). Letterplex uses this for the dialogs. Bouncing when you hit the ground feels so much better than the alternatives!

