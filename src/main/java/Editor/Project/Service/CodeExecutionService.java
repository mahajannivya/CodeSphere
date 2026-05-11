package Editor.Project.Service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class CodeExecutionService {
    public String executeJavaCode(String code,
                                  String input,
                                  String language) {

        String folderName = "temp_" + UUID.randomUUID();

        File folder = null;

        try {

            folder = new File(
                    System.getProperty("user.dir")
                            + "/" + folderName
            );

            folder.mkdir();

            String fileName = "";
            String dockerImage = "";
            String command = "";

            switch (language.toLowerCase()) {

                case "java":

                    fileName = "Main.java";

                    dockerImage = "eclipse-temurin:17";

                    command =
                            "javac /app/Main.java && java -cp /app Main";

                    break;

                case "python":

                    fileName = "main.py";

                    dockerImage = "python:3.10";

                    command = "python3 /app/main.py";

                    break;

                case "cpp":

                    fileName = "main.cpp";

                    dockerImage = "gcc:latest";

                    command =
                            "g++ /app/main.cpp -o /app/main && /app/main";

                    break;

                default:
                    return "Unsupported Language";
            }

            /* WRITE CODE FILE */

            File file = new File(folder, fileName);

            Files.write(file.toPath(), code.getBytes());

            String path =
                    folder.getAbsolutePath().replace("\\", "/");

            ProcessBuilder pb = new ProcessBuilder(

                    "docker", "run", "--rm",

                    "-i",

                    "-v", path + ":/app",

                    dockerImage,

                    "sh", "-c",

                    command
            );

            pb.redirectErrorStream(true);

            Process process = pb.start();

            /* SEND INPUT */

            BufferedWriter writer =
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    process.getOutputStream()
                            )
                    );

            if(input != null &&
                    !input.trim().isEmpty()){

                writer.write(input);

                writer.newLine();
                writer.flush();
            }



            /* READ OUTPUT */

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    process.getInputStream()
                            )
                    );

            StringBuilder output =
                    new StringBuilder();

            String line;

            /* TIMEOUT */

            boolean finished =
                    process.waitFor(15, TimeUnit.SECONDS);

            if(!finished){

                process.destroyForcibly();

                return "Error : Time Limit Exceeded";
            }

            /* READ COMPLETE OUTPUT */

            while((line = reader.readLine()) != null){

                output.append(line)
                        .append("\n");
            }

            String finalOutput =
                    output.toString().trim();
            if(finalOutput.contains("NoSuchElementException")){ return "Input Required"; }


            return finalOutput;

        }

        catch (Exception e){

            return "Execution Error\n"
                    + e.getMessage();
        }

        finally {

            if(folder != null &&
                    folder.exists()){

                deleteFolder(folder);
            }
        }
    }


    private void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
        }
        folder.delete();
    }
}