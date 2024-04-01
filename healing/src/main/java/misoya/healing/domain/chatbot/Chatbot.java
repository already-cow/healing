package misoya.healing.domain.chatbot;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.context.annotation.Profile;

@Data
public class Chatbot {

    @JsonProperty("profile")
    private Profile profile;

    @JsonProperty("talk")
    private Talk talk;


        public static class Profile {

            @JsonProperty("persona-id")
            private String personaId;

            @JsonProperty("persona")
            private Persona persona;

            @JsonProperty("emotion")
            private Emotion emotion;

            public Profile() {
            }
        }

        public static class Persona {

            @JsonProperty("persona-id")
            private String personaId;

            @JsonProperty("human")
            private String[] human;

            @JsonProperty("computer")
            private String[] computer;

            public Persona() {
            }
        }

    public static class Emotion {

        @JsonProperty("emotion-id")
        private String emotionId;

        @JsonProperty("type")
        private String type;

        @JsonProperty("situation")
        private String[] situation;

        public Emotion() {
        }
    }

        public static class Talk {
            @JsonProperty("id")
            private Id id;

            @JsonProperty("content")
            private Content content;

            public Talk() {
            }

        }

        public static class Id {
            @JsonProperty("profile-id")
            private String profileId;

            @JsonProperty("talk-id")
            private String talkId;

            public Id() {
            }

        }


        public static class Content {
            @JsonProperty("HS01")
            private String HS01;

            @JsonProperty("SS01")
            private String SS01;

            @JsonProperty("HS02")
            private String HS02;

            @JsonProperty("SS02")
            private String SS02;

            @JsonProperty("HS03")
            private String HS03;

            @JsonProperty("SS03")
            private String SS03;

            public Content() {
            }

        }
    }
