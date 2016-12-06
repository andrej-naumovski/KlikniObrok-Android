# KlikniObrok.mk
## Mobile-based application for standardized order systems in bars and restaurants.

### Rules for pushing commits
* All initial code commits go in ***dev*** branch
* Pull requests for ***staging*** are made only after ***dev*** branch has been confirmed working
* Pull requests for ***production*** are made only after ***staging*** branch has been tested
* Make more smaller commits instead of one big commit, makes it easier for debugging
* Commit messages should be in present simple, i.e *"Fix view not loading"* instead of *"Fixed view not loading"*
* Make commit messages short and understandable, i.e *"Fix ViewName not loading"* instead of *"Fix ViewName in PackageName package not loading due to /< insert error here />"*
* Try to commit buggy/unfinished code as rarely as possible :)
