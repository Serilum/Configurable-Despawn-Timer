<h2>Configurable Despawn Timer</h2>
<p><a href="https://github.com/Serilum/Configurable-Despawn-Timer"><img src="https://serilum.com/assets/data/logo/configurable-despawn-timer.gif"></a></p><h2>Download</h2>
<p>You can download Configurable Despawn Timer on CurseForge and Modrinth:</p><p>&nbsp;&nbsp;CurseForge: &nbsp;&nbsp;<a href="https://curseforge.com/minecraft/mc-mods/configurable-despawn-timer">https://curseforge.com/minecraft/mc-mods/configurable-despawn-timer</a><br>&nbsp;&nbsp;Modrinth: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://modrinth.com/mod/configurable-despawn-timer">https://modrinth.com/mod/configurable-despawn-timer</a></p>
<h2>Issue Tracker</h2>
<p>To keep a better overview of all mods, the issue tracker is located in a separate repository.<br>&nbsp;&nbsp;For issues, ideas, suggestions or anything else, please follow this link:</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;-> <a href="https://serilum.com/url/issue-tracker">Issue Tracker</a></p>
<h2>Pull Requests</h2>
<p>Because of the way mod loader files are bundled into one jar, some extra information is needed to do a PR.<br>&nbsp;&nbsp;A wiki page entry about it is available here:</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;-> <a href="https://serilum.com/url/pull-requests">Pull Request Information</a></p>
<h2>Mod Description</h2>
<p style="text-align:center"><a href="https://serilum.com/" rel="nofollow"><img src="https://github.com/Serilum/.cdn/raw/main/description/header/header.png" alt="" width="838" height="400"></a></p>
<p style="text-align:center"><a href="https://curseforge.com/members/serilum/projects" rel="nofollow"><img src="https://raw.githubusercontent.com/Serilum/.data-workflow/main/badges/svg/curseforge.svg" width="200"></a> <a href="https://modrinth.com/user/Serilum" rel="nofollow"><img src="https://raw.githubusercontent.com/Serilum/.data-workflow/main/badges/svg/modrinth.svg" width="200"></a> <a href="https://patreon.com/serilum" rel="nofollow"><img src="https://raw.githubusercontent.com/Serilum/.data-workflow/main/badges/svg/patreon.svg" width="200"></a> <a href="https://youtube.com/@serilum" rel="nofollow"><img src="https://raw.githubusercontent.com/Serilum/.data-workflow/main/badges/svg/youtube.svg" width="200"></a></p>
<p><strong><span style="font-size:24px">Requires the library mod&nbsp;<a style="font-size:24px" href="https://curseforge.com/minecraft/mc-mods/collective" rel="nofollow">Collective</a>.</span></strong><br><br> <strong>&nbsp; &nbsp; &nbsp;This mod is part of <span style="color:#008000"><a style="color:#008000" href="https://curseforge.com/minecraft/modpacks/the-vanilla-experience" rel="nofollow">The Vanilla Experience</a></span>.</strong><br><span style="font-size:18px">Configurable Despawn Timer allows items and experience orbs to remain on the ground longer or shorter than the default lifespan. It is highly configurable. There is a global settings file for the default values. You can also set it so that all items dropped by players will remain on the ground infinitely. And lastly a config file is generated after the first world load, containing all item names. Here you can set a despawn time for any item specifically. There is a command to reload any changes.<br><br>Minecraft's default despawn time is 5 minutes. The mod's default is 10 minutes for both items and experience orbs.</span><br><br>The ItemExpireEvent will still run after the altered lifespan, making it compatible with other mods.<br><br><br><strong><span style="font-size:20px">Configurable:</span> <span style="color:#008000;font-size:14px"><a style="color:#008000" href="https://github.com/Serilum/.information/wiki/how-to-configure-mods" rel="nofollow">(&nbsp;how do I configure?&nbsp;)</a></span><br></strong><span style="font-size:12px"><strong>globalItemDespawnTimeInTicks</strong>&nbsp;(default = 12000, min 1, max 2147483647): The delay in ticks when an item should despawn. Minecraft's default time is 6000 ticks. 1 second is 20 ticks.</span><br><span style="font-size:12px"><strong>globalExperienceOrbDespawnTimeInTicks</strong>&nbsp;(default = 12000, min 1, max 2147483647): The delay in ticks when experience orbs should despawn. Minecraft's default time is 6000 ticks. 1 second is 20 ticks.</span><br><span style="font-size:12px"><strong>preventDespawnForPlayerItems</strong>&nbsp;(default = false): Whether items thrown/dropped by players should not despawn at all. Works for manual dropping and also on death. Overrides the other time settings.</span></p>
<p><br><span style="font-size:24px"><strong>Commands:</strong></span><br><span style="font-size:14px"><em>/cdt usage</em></span> - Shows the usage of commands.<br><span style="font-size:14px"><em>/cdt reload</em></span> - Reloads all manual changes to the despawn timer config file.<br><br><br><span style="font-size:24px"><strong>Item specific settings:<br></strong></span><span style="font-size:14px">It is possible to set despawn times for specific items as well. When you first generate or load a world, a file is created located in '<em><strong>./config/configurabledespawntimer</strong></em>' called '<em><strong>specific_despawn_times.txt</strong></em>'.<br><br>A value of -1 means it will use the global settings, 0 means the item won't despawn at all, and any other positive value will be the despawn time in ticks. 20 ticks = 1 second.</span><br><picture><img src="https://github.com/Serilum/.cdn/raw/main/projects/configurable-despawn-timer/a.png"></picture><br><br>------------------<br><br><span style="font-size:24px"><strong>You may freely use this mod in any modpack, as long as the download remains hosted within the CurseForge or Modrinth ecosystem.</strong></span><br><br><span style="font-size:18px"><a style="font-size:18px;color:#008000" href="https://serilum.com/" rel="nofollow">Serilum.com</a> contains an overview and more information on all mods available.</span><br><br><span style="font-size:14px">Comments are disabled as I'm unable to keep track of all the separate pages on each mod.</span><span style="font-size:14px"><br>For issues, ideas, suggestions or anything else there is the&nbsp;<a style="font-size:14px;color:#008000" href="https://github.com/Serilum/.issue-tracker" rel="nofollow">Github repo</a>. Thanks!</span><span style="font-size:6px"><br><br></span></p>
<p style="text-align:center"><a href="https://serilum.com/donate" rel="nofollow"><img src="https://github.com/Serilum/.cdn/raw/main/description/projects/support.svg" alt="" width="306" height="50"></a></p>