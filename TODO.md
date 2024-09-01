# TODO

## MVP
 - [x] Bus screen
   - selection for buslines and date
 - [x] Program screen
   - with a selection for location and date
 - [x] Info Screen
 - [x] Search Screen
 - [x] Map Screen
   - [x] Map with all clubs
 - [ ] Details Screen
   - [ ] Detail map for 1+ club(s) showing all their offerings
   - [ ] navigation to Details from Search Screen
   - [ ] navigation to Details from Search Screen

## Post-MVP
 - [ ] Fix orientation change issue
 - [ ] Fix (too many) recompositions in BusScreen
 - [ ] Enable compose lint
 - [ ] Instagram Deep Link
   - https://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
   - https://stackoverflow.com/questions/15497261/open-instagram-user-profile
 - [ ] replace hardcoded, manually counted number in InfoScreen with dynamic SQL query
 - [ ] Search Screen: Use mulit selection FilterChips instead of dropdown menu 
 - [ ] Create Feature modules for all screens
 - [ ] Make Program Screen look like this https://www.maifeld-derby.de/timetable
 - [ ] Unify ProgramScreenSuccess with "SelectionScreen" into one Composable component
 - [ ] Add Preview for Selection and SelectionCard
 - [ ] Use IDs in Detail Screen instead of name to select corresponding detail item (club, offer)