package draft.dev;

//Assembly AI Imports
import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import com.assemblyai.api.resources.lemur.requests.LemurTaskParams;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.List;

public class Main {

    public static void main(String[]args) {
        AssemblyAI client = AssemblyAI.builder().apiKey("40caa1cc739c412a9cb69780a7ae041a").build(); //Remember to remove API Key

        try {
            File audioFile = new File("./audio/apple_public_earnings_call.flac");
            // Create a new output file and filewriter for the transcription
            File outputFile = new File("output.txt");
            outputFile.createNewFile();
            FileWriter fileWriter = new FileWriter(outputFile);
            // Transcribe audio file
            Transcript transcript = client.transcripts().transcribe(audioFile);


            // Checks to see if transcription was successful
            if (transcript.getStatus().toString().contentEquals("error")) {
                System.err.println(transcript.getError());
            } else {
                // Write transcript to output file
                fileWriter.write(String.valueOf(transcript.getText()));
                fileWriter.close();
                // Opens output file
                Desktop desktop = Desktop.getDesktop();
                desktop.open(outputFile);

                // Generate prompt
                var params = LemurTaskParams.builder()
                        .prompt("Extract insights from the transcript.").transcriptIds(List.of("8f072a17-0a8b-40ce-ba15-5e9fc5e90daa"))
                        .build();
                // Execute prompt
                var result = client.lemur().task(params);

                // Print response
                System.out.println(result.getResponse());


            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}
