# Low Health Resistance

Fabric mod for Minecraft **1.21.11**.

- When your health is **1 heart (2 HP) or less**, you automatically get **infinite Resistance II**.
- When you heal up to **2 hearts (4 HP) or more**, the effect is removed.
- If you get hit and drop back to 1 heart, it comes back automatically.
- If milk removes it while you are still at 1 heart, it is re-applied.

Needs Fabric Loader and Fabric API. Works in singleplayer and on servers (install on the server; clients do not need it).

## Build the JAR with GitHub

1. Create a new GitHub repository and upload everything from this folder (keep the `.github` folder).
2. Open the **Actions** tab. The build runs on every push (or press **Run workflow**).
3. When it finishes, open the run and download **LowHealthResistance-jar** from Artifacts.
4. Put `low-health-resistance-1.0.0.jar` (not the `-sources` one) into your `mods` folder.

## Settings

Edit `LowHealthResistance.java`:

- `AMPLIFIER` - `0` = Resistance I, `1` = Resistance II (default), `2` = Resistance III
- `APPLY_AT_OR_BELOW` / `REMOVE_AT_OR_ABOVE` - health thresholds in HP (2 HP = 1 heart)
