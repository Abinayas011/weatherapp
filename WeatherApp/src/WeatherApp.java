import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class WeatherApp extends Frame {
    TextField cityField;
    Label weatherLabel;

    public WeatherApp() {
        // Set up the frame
        setTitle("Travel Weather App");
        setSize(400, 200);
        setLayout(new FlowLayout());
        setVisible(true);

        // Create UI components
        Label cityLabel = new Label("Enter City: ");
        cityField = new TextField(20);
        Button getWeatherButton = new Button("Get Weather");
        Button infoButton = new Button("Info");
        weatherLabel = new Label("Weather: -");

        // Add components to the frame
        add(cityLabel);
        add(cityField);
        add(getWeatherButton);
        add(infoButton);
        add(weatherLabel);

        // Add event listener to the "Get Weather" button
        getWeatherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityField.getText();
                if (!city.isEmpty()) {
                    String weather = getWeather(city);
                    weatherLabel.setText("Weather: " + weather);
                } else {
                    weatherLabel.setText("Please enter a city.");
                }
            }
        });

        // Add event listener to the "Info" button
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInfoDialog();
            }
        });

        // Add window closing functionality
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    // Method to fetch weather data
    private String getWeather(String city) {
        try {
            String apiKey = "20d05248cc6c16bfa70b8b7344b08b4f"; // Replace with your API key
            String apiUrl = "https://dashboard.openweather.co.uk/dashboard/settings/new?firstLogin=true" + city + "&appid=" + apiKey + "&units=metric";
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON response (simple parsing for demonstration)
            String responseString = response.toString();
            int tempIndex = responseString.indexOf("\"temp\":");
            if (tempIndex != -1) {
                int tempEnd = responseString.indexOf(",", tempIndex);
                String temperature = responseString.substring(tempIndex + 7, tempEnd);
                return temperature + "°C";
            }
            return "Data not found";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching weather";
        }
    }

    // Method to display an information dialog
    private void showInfoDialog() {
        Dialog infoDialog = new Dialog(this, "About Travel Weather App", true);
        infoDialog.setSize(300, 150);
        infoDialog.setLayout(new FlowLayout());

        Label infoLabel = new Label("This app provides weather information for any city.");
        Label apiLabel = new Label("Powered by OpenWeatherMap API.");
        Button closeButton = new Button("Close");

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoDialog.setVisible(false);
            }
        });

        infoDialog.add(infoLabel);
        infoDialog.add(apiLabel);
        infoDialog.add(closeButton);
        infoDialog.setVisible(true);
    }

    public static void main(String[] args) {
        new WeatherApp();
    }
}

