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

License
=======

All other code is distributed under the terms below:

Copyright 2014 Ralph Thomas

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


