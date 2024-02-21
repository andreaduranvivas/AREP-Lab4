package edu.arep.myspring.myspark.nuevasFunciones;

public class StaticFiles {
    private String directory;

    public StaticFiles() {
        this.directory = "target/classes/public";
    }

    /**
     * Sets the directory to the specified path.
     *
     * @param path the path to set as the directory
     */
    public void location(String path) {
        this.directory = path;
    }

    public String getDirectory() {
        return directory;
    }
}
