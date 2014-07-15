package FileManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.StandardCopyOption.*;

public class FileManager {
    private Path currentDirectory;

    public FileManager() {
        File file = new File(".");
        setCurrentDirectory(file.getAbsolutePath());
    }

    private Path getFilePathFromString(String fileName) {
        Path filePath = Paths.get(fileName);
        if (!filePath.isAbsolute()) {
            filePath = currentDirectory.resolve(filePath);
        }

        filePath = filePath.normalize();

        return filePath;
    }

    private void removeDir(File dirToRemove) {
        File[] content = dirToRemove.listFiles();

        for (File nextEntry: content) {
            if (nextEntry.isDirectory()) {
                removeDir(nextEntry);
            }

            nextEntry.delete();
        }

        dirToRemove.delete();
    }

    public void setCurrentDirectory(String newDirectory) {
        Path newCurrentDirectory = getFilePathFromString(newDirectory);

        if (!newCurrentDirectory.toFile().isDirectory()) {
            throw new IllegalArgumentException(newDirectory + ": no such directory");
        }

        currentDirectory = newCurrentDirectory;
    }

    public String getCurrentDirectory() {
        return currentDirectory.toString();
    }

    public String[] getCurrentDirectoryContent() {
        return currentDirectory.toFile().list();
    }

    public void remove(String fileName) {
        File fileToRemove = getFilePathFromString(fileName).toFile();
        if (!fileToRemove.exists()) {
            throw new IllegalArgumentException("cannot remove " + fileName + ": no such file or directory");
        }

        if (fileToRemove.isFile()) {
            fileToRemove.delete();
        } else {
            removeDir(fileToRemove);
        }
    }

    public void createNewDirectory(String newDirectoryName) {
        Path newDirectoryPath = getFilePathFromString(newDirectoryName);
        File newDirectoryFile = newDirectoryPath.toFile();

        if (newDirectoryFile.exists()) {
            throw new IllegalArgumentException("Directory " + newDirectoryName + " already exists");
        }

        newDirectoryFile.mkdir();
    }

    public void moveFiles(String sourceFileName, String destinationFileName) throws IOException {
        Path sourcePath = getFilePathFromString(sourceFileName);
        Path destinationPath = getFilePathFromString(destinationFileName);

        if (sourcePath.toFile().equals(destinationPath.toFile())) {
            throw new IllegalStateException("Files are same");
        }

        if (sourcePath.toFile().isDirectory() && destinationPath.toFile().isFile()) {
            throw new IllegalStateException("Cannot overwrite non-directory " + destinationFileName +
                                            "with directory " + sourceFileName);
        }

        Files.move(sourcePath, destinationPath, REPLACE_EXISTING);
    }

    public void copyFiles(String sourceFileName, String destinationFileName) throws IOException {
        final Path sourcePath = getFilePathFromString(sourceFileName);
        final Path sourcePathParent = sourcePath.getParent();
        final Path destinationPath = getFilePathFromString(destinationFileName);

        if (!Files.exists(sourcePath)) {
            throw new IllegalStateException(sourcePath + " : no such file or directory");
        }

        if (sourcePath.toFile().equals(destinationPath.toFile())) {
            throw new IllegalStateException("Can not copy " + sourceFileName + " to " +
                                            destinationFileName + " : files are same");
        }

        if (Files.isRegularFile(destinationPath)) {
            if (Files.isRegularFile(sourcePath)) {
                Files.copy(sourcePath, destinationPath);

                return;
            }

            if (Files.isDirectory(sourcePath)) {
                throw new IllegalStateException("Cannot overwrite non-directory " +
                                                destinationFileName + " with directory " + sourceFileName);
            }
        }

        if (Files.isRegularFile(sourcePath) && !Files.exists(destinationPath)) {
            Files.copy(sourcePath, destinationPath);

            return;
        }

        if (!Files.exists(destinationPath)) {
            Files.createDirectory(destinationPath);
        }

        Files.walkFileTree(sourcePath, new FileVisitor<Path>() {
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path relative = sourcePathParent.relativize(dir);
                Path destinationDir = destinationPath.resolve(relative);

                if (!destinationDir.equals(destinationPath) || !Files.exists(destinationDir)) {
                    Files.createDirectory(destinationDir);
                }

                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relative = sourcePathParent.relativize(file);
                Path destinationFile = destinationPath.resolve(relative);

                if (Files.exists(destinationFile)) {
                    throw new IOException(destinationFile.toString() + " : file already exists");
                }

                Files.copy(file, destinationFile);

                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult visitFileFailed(Path file, IOException exception) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult postVisitDirectory(Path dir, IOException exception) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
