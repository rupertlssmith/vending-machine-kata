Vending Machine
===============

Build Instructions
------------------

Issue the command:

    mvn clean test

This will build the code and run the tests. This also includes running some mutation tests to verify the quality of the tests themselves.

Notes
-----

Modelling the On/Off state in software is a little weird. Presumably a real machine when off has no software running at all, therefore code cannot make checks such as not allowing coins to be inserted when the machine is off. For the purposes of this exercise I will assume that this is a pure software machine - perhaps it is a software module that can be deployed to vending machine hardware where the on/off state of the module is a different lifecycle to the on/off power state of the machine itself.

There is no deliberate restriction on the amount of money that can be inserted. A lot of money could be inserted until 'int' overflows or memory runs out.

When the machine is switched on the initial balance is always zero. This means that power cycling the machine will eat any money already inserted. Customers may not be happy with this if there is a power glitch and they lose their money. I will assume this is a very low probability event. We could add a feature to persist the machine state prior to power off and restore it on power on. This will require a database to hold the state, or perhpas a battery backed memory. Either way it is a feature with a high effort to implement and a low value, so lets leave it in the product back-log for now.

The specification does not ask for actions to restock the machine. It seems fairly obvious that a real machine will need to be restocked and having these available as actions is going to make it simpler to put the machine into all the test states needed to fully cover its behaviour. This ommission in the specification would be fed back to the business analyst during backlog refinement. Time needs to be found to bring the feature specification up to par with the 'definition of ready' by sprint planning or the feature risks being delayed.

The initial stock after switching the machine on is zero. The actual stock left in the machine whilst it was off may be recovered from a database or battery backed RAM or perhaps by physically scanning the contents. This seems like a high value feature but also one that will require some effort to implement. I will leave this in the backlog for now, but would expect the team to be discussing it early on in the project and coming up with a solution and breaking that solution down into smaller pieces.

Other than being off, there are 3 reasons that the machine will not allow an item to be vended; the requested item is out of stock; the user has not inserted enough money; the machine does not have the coins to make the correct change. These are represented as 3 distinct exceptions that the business logic can produce. It is expected that an appropriate error message will be given back to the user in the event of one of them occurring. If the user cannot correct the error they can reset the machine by returning any coins inserted so far.

It is possible that multiple reasons to fail to vend can be present at the same time. To avoid reporting more than one error at once, the reasons are checked in the following order:

1. Is sufficient stock available? This is checked first as inserting more money can't fix this.
2. Has enough money been inserted? This is checked next as there is no point in trying to make change for a negative balance.
3. Is change available?

