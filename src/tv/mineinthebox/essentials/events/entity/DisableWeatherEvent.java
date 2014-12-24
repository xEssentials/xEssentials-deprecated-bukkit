package tv.mineinthebox.essentials.events.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class DisableWeatherEvent implements Listener {
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		if(e.toWeatherState()) {
			e.setCancelled(true);	
		}
	}

}
