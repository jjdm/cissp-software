import sys

print("Program started.")

print("Basic Numbering Example:")
for i in range(11):
	print("  The number is: " + str(i));
	
print("Filtering Example:");
food = ["Pizza", "Ice Cream", "Pineapple", "Sandwich"]
pFood = [f for f in food if f.startswith("P")]
for f in pFood: print("  " + str(f))

print("Your arguments:");
for argument in sys.argv:
	print("  " + str(argument))
	
print("Program ended.")