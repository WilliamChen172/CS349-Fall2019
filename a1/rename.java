import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;

/* Parse command line arguments
 * Assume that they always appear as a key:value pair, separated by spaces
 * Store each key:value pair in a HashMap for easy lookup during runtime
 */

class Storage {
    HashMap<String, ArrayList<String>> arguments;
    ArrayList<String> order;
}

class rename {
    public static void main(String[] args) {
        if (args.length == 0) {
            printHelp();
        } else {
            Storage parsedArgs = parse(args);
            execute(process(parsedArgs));
        }
    }

    // Print help option
    private static void printHelp() {
        System.out.println("(c) 2019 William Chen. Last revised: Sept 26, 2019.");
        System.out.println("Usage: java rename [-option argument1 argument2 ...]\n");
        System.out.println("Options:");
        System.out.println(" -help                     :: print out a help page and exit the program.");
        System.out.println(" -prefix [string]          :: rename [filename] so that it starts with [string].");
        System.out.println(" -suffix [string]          :: rename [filename] so that it ends with [string].");
        System.out.println(" -replace [str1] [str2]    :: rename [filename] by replacing all instances of [str1] with [str2].");
        System.out.println(" -file [filename]          :: denotes the [filename] to be modified.");
    }

    // Build a dictionary of key:value pairs (without the leading "-")
    private static Storage parse(String[] args) {
        Storage storage = new Storage();

        HashMap<String, ArrayList<String>> arguments = new HashMap<>();
        ArrayList<String> order = new ArrayList<>();

        String key = "noOption";
        ArrayList<String> value = new ArrayList<>();

        // process each argument as either a key or value in the pair
        for (String entry : args) {

            // assume that all options start with a dash
            if (entry.startsWith("-")) {

                // reset key and value if it's a new option
                if (!key.equals("")) {
                    key = "";
                    value.clear();
                }

                // if the key is -help, print out a help page and exit the program.
                if (entry.equals("-help")) {
                    printHelp();
                    System.exit(0);
                }

                // if the key is any other valid option, store the command as key/value
                // pairs and store in hashMap to execute later.
                key = entry.substring(1);

            } else {
                // Everything without a dash is an argument

                // check for special arguments such as @time or @date
                if (entry.equals("@date")) {
                    DateTimeFormatter dft = DateTimeFormatter.ofPattern("MM-dd-YYYY");
                    entry = java.time.LocalDate.now().format(dft);
                }

                if (entry.equals("@time")) {
                    DateTimeFormatter dft = DateTimeFormatter.ofPattern("HH-mm-ss");
                    entry = java.time.LocalTime.now().format(dft);
                }

                // add the value to current values
                value.add(entry);
            }

            // Process the current key/value pair and store them in hashMap
            if (key.equals("file")) {
                if (arguments.containsKey(key)) {
                    if (arguments.get(key) != null) {
                        value.addAll(arguments.get(key));
                        ArrayList<String> newValue = new ArrayList<>(value);
                        arguments.put(key, newValue);
                    } else {
                        ArrayList<String> newValue = new ArrayList<>(value);
                        arguments.put(key, newValue);
                    }
                } else {
                    arguments.put(key, null);
                    order.add(key);
                }

                value.clear();
            } else {
                while (arguments.containsKey(key) && value.isEmpty()) {
                    key = key.concat("s");
                }

                if (value.isEmpty()) {
                    arguments.put(key, null);
                    order.add(key);
                } else {
                    ArrayList<String> newValue = new ArrayList<>(value);
                    arguments.put(key, newValue);
                }
            }
        }

        storage.arguments = arguments;
        storage.order = order;
        return storage;
    }

    private static Storage process(Storage storage) {
        // process the parsed arguments and output proper errors to the user
        // traverse through all the key/value pairs and
        // 1. rule out all invalid options
        // 2. make sure every option has the correct number of arguments
        // 3. there must be a -file option as well as one other option for every input
        boolean hasFile = false;
        boolean hasOption = false;
        boolean hasError = false;

        HashMap<String, ArrayList<String>> arguments = storage.arguments;
        ArrayList<String> order = storage.order;

        if (arguments.keySet().contains("noOption")) {
            for (String value: arguments.get("noOption")) {
                System.err.println("Invalid input " + value + ". Please specify an option for this input.");
            }
            arguments.remove("noOption");
            order.remove("noOption");
            hasError = true;
        }

        for (String key: order) {

            ArrayList<String> values = arguments.get(key);
            String keyType;

            // if the program has invalid options, or options with invalid arguments, print out specifically which
            //  option is incorrect and exit
            if (key.startsWith("prefix")) {
                hasOption = true;
                keyType = "prefix";
            } else if (key.startsWith("suffix")) {
                hasOption = true;
                keyType = "suffix";
            } else if (key.startsWith("replace")) {
                hasOption = true;
                keyType = "replace";
            } else {
                keyType = key;
            }

            if (!keyType.equals("prefix") && !keyType.equals("suffix") && !keyType.equals("replace") && !keyType.equals("file")) {
                System.err.println(keyType + " is an invalid option.");
                hasError = true;
            } else if (values == null) {
                System.err.println("Option '-" + keyType + "' does not have correct number of arguments.");
                hasError = true;
                continue;
            }

            if (key.startsWith("replace") && values.size() != 2) {
                System.err.println("Option '-replace' does not have correct number of arguments.");
                hasError = true;
                continue;
            }

            if (keyType.equals("file")) {
                // if the file name cannot be processed(doesn't exist or permissions error), inform and exit
                //  use canWrite() to achieve this
                for (String fileName: values) {
                    File file = new File(fileName);
                    if (!file.exists()) {
                        System.err.println("File " + fileName + " does not exist.");
                        hasError = true;
                    } else if (!file.canWrite()) {
                        System.err.println("File " + fileName + " does not have permission to be renamed.");
                        hasError = true;
                    }
                }

                if (!values.isEmpty()) {
                    hasFile = true;
                }
            }
        }

        // if the program is not supplied with sufficient number of arguments, print help and exit
        if (!hasFile) {
            System.err.println("No file names are provided for the program.");
        }

        if (!hasOption) {
            System.err.println("No options are provided for the program.");
        }

        if (!hasFile || !hasOption) {
            System.err.println("The program does not have a sufficient number of arguments.");
            hasError = true;
        }

        if (hasError) {
            printHelp();
            System.exit(0);
        }

        // if everything works return the processed hashMap
        return storage;
    }

    private static void execute(Storage storage) {
        HashMap<String, ArrayList<String>> args = storage.arguments;
        ArrayList<String> order = storage.order;
        ArrayList<String> files = args.get("file");

        for (String fileName : files) {
            int fileTypeIndex = fileName.indexOf(".");
            ArrayList<String> fileNameSequence = new ArrayList<>();
            String currentFileName;
            String nextFileName;
            String fileType;
            File currentFile = new File(fileName);
            File nextFile;

            if (fileTypeIndex == -1) {
                fileType = "";
                currentFileName = fileName;
            } else {
                fileType = fileName.substring(fileTypeIndex);
                currentFileName = fileName.substring(0, fileTypeIndex);
            }

            for (String key : order) {
                if (key.equals("file")) {
                    continue;
                }

                if (key.startsWith("prefix")) {
                    for (int i = args.get(key).size() - 1; i >= 0; i--) {
                        String value = args.get(key).get(i);
                        nextFileName = value.concat(currentFileName).concat(fileType);
                        nextFile = new File(nextFileName);

                        fileNameSequence.add(nextFileName);
                        currentFileName = updateFileName(nextFileName);
                        currentFile = new File(nextFileName);

                    }
                } else if (key.startsWith("suffix")) {
                    for (String value : args.get(key)) {
                        nextFileName = currentFileName.concat(value).concat(fileType);
                        nextFile = new File(nextFileName);

                        fileNameSequence.add(nextFileName);
                        currentFileName = updateFileName(nextFileName);
                        currentFile = new File(nextFileName);

                    }
                } else if (key.startsWith("replace")) {
                    String oldString = args.get(key).get(0);
                    String newString = args.get(key).get(1);
                    nextFileName = currentFileName.concat(fileType).replace(oldString, newString);
                    nextFile = new File(nextFileName);

                    fileNameSequence.add(nextFileName);
                    currentFileName = updateFileName(nextFileName);
                    currentFile = new File(nextFileName);

                } else {
                    System.err.println("Invalid argument.");
                    System.exit(0);
                }
            }

            // make sure the new file name isn't already taken by another file
            File inputFile = new File(fileName);
            File outputFile = new File(fileNameSequence.get(fileNameSequence.size() - 1));
            if (outputFile.exists()) {
                System.err.println(outputFile.getName() + " already exists. Please choose a different name to rename.");
                System.exit(0);
            }

            // start renaming
            currentFileName = fileName;
            for (int i = 0; i < fileNameSequence.size() - 1; i++) {
                nextFileName = fileNameSequence.get(i);

                System.out.println("Renamed " + currentFileName + " to " + nextFileName + " successfully.");
                currentFileName = nextFileName;
            }
            try {
                if (inputFile.renameTo(outputFile)) {
                    System.out.println("Renamed " + currentFileName + " to " + outputFile.getName() + " successfully.");
                } else {
                    System.err.println("Rename " + currentFileName + " to " + outputFile.getName() + " unsuccessful.");
                }
            } catch (Exception ex) {
                System.err.println("Rename " + currentFileName + " to " + outputFile.getName() + " unsuccessful: " + ex.toString() + ".");
            }
        }
    }

    private static String updateFileName(String file) {
        int fileTypeIndex = file.indexOf(".");
        String fileName;

        if (fileTypeIndex == -1) {
            fileName = file;
        } else {
            fileName = file.substring(0, fileTypeIndex);
        }

        return fileName;
    }
}