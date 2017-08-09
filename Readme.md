
Modelling the On/Off state in software is a little weird. Presumably a real machine when off has no software running at all, therefore code cannot make checks such as not allowing coins to be inserted when the machine is off. For the purposes of this exercise I will assume that this is a pure software machine - perhaps it is a software module that can be deployed to vending machine hardware where the on/off state of the module is a different lifecycle to the on/off power state of the machine itself.

There is no deliberate restriction on the amount of money that can be inserted. A lot of money could be inserted until 'int' overflows or memory runs out.

When the machine is switched on the initial balance is always zero. This means that power cycling the machine will eat any money already inserted. Customers may not be happy with this if there is a power glitch and they lose their money. I will assume this is a very low probability event. We could add a feature to persist the machine state prior to power off and restore it on power on. This will require a database to hold the state, or perhpas a battery backed memory. Either way it is a feature with a high effort to implement and a low value, so lets leave it in the product back-log for now.

