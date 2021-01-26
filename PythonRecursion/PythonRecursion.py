def pythonRecursion():
    string = raw_input("Please enter a string: ")
    substring = raw_input("Please enter the substring you wish to find: ")
    newString = raw_input("Please a string to replace the given substring: ")
    finalString = string.replace(substring, newString)
    print("Your new string is: " + finalString)
    pythonRecursion()

pythonRecursion()
