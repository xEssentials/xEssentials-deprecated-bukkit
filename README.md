<h1>xEssentials v3.5</h1>

a new bukkit plugin with alot of new features this plugin is still sort of unofficial and it will not be encouraged to use this on your server as its still not stable to use on a production server.

on future note: the project name might will change over time.

currently the configurations and features are:

#Entity management:
with this you can manage entity spawning, per biome or per world base you can also fully prevent them to spawn.
also you can block all kinds of grief behaviour such as creeper explosions, withers, and many more. 

#Command management: 
since the plugin is sort of getting bigger and bigger we think a way to disable commands only from the plugin against conflicts, is excellent.
its also possible to create tasked commands.
all commands can be found here: https://github.com/xEssentials/xEssentials/tree/master/src/tv/mineinthebox/bukkit/essentials/commands

#Portals:
this plugin comes with portals and very easy to create them and to delete them.

#Chat:
this plugin allows players to highlight people in the chat like @Xeph0re the @ can be customized and also offline players can be
highlightned by using tab complete, there is spam prevention in place aswell, including alternate checking through fishbans api.

#Ban management:
like every other essentials like plugin this is nothing new, however we thought of adding a few api's to check if the person listed
on a ban site such like mcbans, mcbouncer through the fishbans api, however banning players does not use the fishbans api.

#Shops:
we have added our own shop system in our plugin, actually its very smilliar to chestshop however the admin shops open a inventory
with villager sounds, this is configurable you could also disable the whole shop system.

#Economy:
we have added our own Economy system which is Vault compatible and UUID compatible.

#Protection chests and more:
we have added protection to chests and other blocks where you would expect it to, also noteblocks etc.

#Misc:
we have added gates, bridges, elevators and many more!

#Greylist:
we have implemented our own http socket server allowing to sent back a json response.
since we weren't able to find a good plugin suiteable with this idea in mind:
<p>`[quiz->if good?->go to url->player is now greylisted]`</p>
we decided to make it, this is fully configurable.
an example how to make such json request through php is easily done by doing this:
```
	 <?php
		$string = file_get_contents("http://127.0.0.1:8001/adduser/Xeph0re"); <- this is the minecraft server
		$json = json_decode($string, true);
		$args = $json\["xEssentials"\]\["response"\]; <- remove backslashes here eclipse erroring this out.
		if($args == "success") {
			echo "player has been promoted";
		} else if($args == "greylisted") {
			echo "player already is greylisted";
		} else if($args == "notexist") {
			echo "player has never played before";
		}
	?>
```

#RSS feeds and mojang status:
we've made 2 configurable crawlers one sents a broadcast every few minutes if the session server is down or the skin server is down(this feature may gets removed later), the other could scan your own website (we recommend using xenforo) which reads your own defined 'news' as a rss url and broadcasts it live as soon when a new topic is made in the hope people would react to it.