Architecture of the application was initially designed to support old way of write android applications and incrementally move one rxjava paradigm with full support of testing (dagger). Current status in the middle of migration and one screen still have loaders instead of Observable source of data. 

App covers usage of rxjava, persisten storage via sqlite db and will be extended by usage of data binding. 

There are several unit tests. They cover biz logic how to present data from the server. It is covered by tests as the first priority because during development lifecycle biz logic part changes the most frequently among others layers and therefore has the most hight risk hide the bug.  

It would be good to have animation during scrolling for details screen (some work in progress you can find in the branch), support of the user id and back comaptibilit for transition animation and elevation.  
