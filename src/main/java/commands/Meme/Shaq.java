package commands.Meme;

import com.google.gson.Gson;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;
import utils.Config;
import utils.Prefix;
import utils.memes.ImgData;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Shaq {

    public String getName() {
        return "shaq";
    }

    public String getDescription() {
        return "Generates UNDERSTANDABLE, HAVE A GREAT DAY meme";
    }

    public String getCategory() {
        return "\uD83E\uDD23 Meme";
    }

    public CommandData slashCommand() {
        return new CommandData(this.getName(), this.getDescription()).addOption(OptionType.STRING, "text1", "Upper Text", true);
    }

    public void run(MessageReceivedEvent event, @NotNull ArrayList<String> args) throws IOException, InterruptedException {
        if (args.toArray().length < 1) {
            event.getMessage().reply(String.format("Please input a text. `%s%s text1`", new Prefix().getPrefix(), this.getName())).mentionRepliedUser(false).queue();
            return;
        }


        Map<Object, Object> data = new HashMap<>();
        data.put("template_id", "253164453");
        data.put("username", new Config().getConfig().getProperty("IMG_USERNAME"));
        data.put("password", new Config().getConfig().getProperty("IMG_PASSWORD"));
        data.put("font", "arial");
        data.put("boxes[0][text]", String.join(" ", args));
        data.put("boxes[0][outline_color]", "#ffffff");
        data.put("boxes[0][color]", "#000000");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://api.imgflip.com/caption_image?%s", buildFormDataFromMap(data))))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ImgData result = new Gson().fromJson(response.body(), ImgData.class);

        event.getMessage().reply(result.data.url).mentionRepliedUser(false).queue();
    }

    public void runSlashCommand(@NotNull SlashCommandEvent event) throws IOException, InterruptedException {
        String text = event.getOptions().get(0).getAsString();

        Map<Object, Object> data = new HashMap<>();
        data.put("template_id", "253164453");
        data.put("username", new Config().getConfig().getProperty("IMG_USERNAME"));
        data.put("password", new Config().getConfig().getProperty("IMG_PASSWORD"));
        data.put("font", "arial");
        data.put("boxes[0][text]", text);
        data.put("boxes[0][outline_color]", "#ffffff");
        data.put("boxes[0][color]", "#000000");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://api.imgflip.com/caption_image?%s", buildFormDataFromMap(data))))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ImgData result = new Gson().fromJson(response.body(), ImgData.class);

        event.reply(result.data.url).queue();
    }

    private static @NotNull String buildFormDataFromMap(@NotNull Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }

        return builder.toString();
    }
}
