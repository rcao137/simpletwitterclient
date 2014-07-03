# Android Simple Twitter Client Project 

This is an Android application for Simple Twitter Client

Time spent: 17 hours spent in total

Completed user stories:

 * [x] Required: User can sign in to Twitter using OAuth login
 * [x] Required: User can view the tweets from their home timeline
       - User is able to see the username, name, body and timestamp for each tweet
       - User can see the relative timestamp for a tweet "8m", "7h"
       - User can view more tweets as they scroll with infinite pagination
       - Optional: Links in tweets are clickable and will launch the web browser 
 * [x] Required: User can compose a new tweet
       - User can click a “Compose” icon in the Action Bar on the top right
       - User can then enter a new tweet and post this to twitter
       - User is taken back to home timeline with new tweet visible in timeline
       - Optional: User can see a counter with total number of characters left for tweet

Questions & Issues:
Once I switch from I switch from normal ListView to PullToRefreshListView, it impacted
 the offset passed into customLoadMoreDataFromApi. The offset becomes "1". Still debugging ...

Notes:

Walkthrough of all user stories:

![Video Walkthrough](simpletwitterclient-2.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).
