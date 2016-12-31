package com.avrgaming.civcraft.command.team;

import org.bukkit.entity.Player;

import com.avrgaming.civcraft.arena.Arena;
import com.avrgaming.civcraft.arena.ArenaManager;
import com.avrgaming.civcraft.arena.ArenaTeam;
import com.avrgaming.civcraft.command.CommandBase;
import com.avrgaming.civcraft.config.CivSettings;
import com.avrgaming.civcraft.exception.CivException;
import com.avrgaming.civcraft.main.CivGlobal;
import com.avrgaming.civcraft.main.CivMessage;
import com.avrgaming.civcraft.object.Resident;
import com.avrgaming.civcraft.questions.JoinTeamResponse;
import com.avrgaming.civcraft.util.CivColor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.avrgaming.civcraft.config.CivSettings;
import com.avrgaming.civcraft.config.ConfigArena;
import com.avrgaming.civcraft.config.ConfigArenaTeam;
import com.avrgaming.civcraft.exception.CivException;
import com.avrgaming.civcraft.exception.InvalidConfiguration;
import com.avrgaming.civcraft.loreenhancements.LoreEnhancement;
import com.avrgaming.civcraft.lorestorage.LoreCraftableMaterial;
import com.avrgaming.civcraft.main.CivGlobal;
import com.avrgaming.civcraft.main.CivMessage;
import com.avrgaming.civcraft.object.Resident;
import com.avrgaming.civcraft.util.BlockCoord;
import com.avrgaming.civcraft.util.CivColor;
import com.avrgaming.civcraft.util.ItemManager;

public class TeamCommand  extends CommandBase {

	@Override
	public void init() {
		command = "/team";
		displayName = CivSettings.localize.localizedString("cmd_team_name");
		
		commands.put("info", CivSettings.localize.localizedString("cmd_team_infoDesc"));
		commands.put("show", CivSettings.localize.localizedString("cmd_team_showDesc"));
		commands.put("create", CivSettings.localize.localizedString("cmd_team_createDesc"));
		commands.put("leave", CivSettings.localize.localizedString("cmd_team_leaveDesc"));
		commands.put("disband", CivSettings.localize.localizedString("cmd_team_disbandDesc"));
		commands.put("add", CivSettings.localize.localizedString("cmd_team_addDesc"));
		commands.put("remove", CivSettings.localize.localizedString("cmd_team_removeDesc"));
		commands.put("changeleader", CivSettings.localize.localizedString("cmd_team_changeleaderDesc"));
		commands.put("arena", CivSettings.localize.localizedString("cmd_team_arenaDesc"));
		commands.put("top5", CivSettings.localize.localizedString("cmd_team_top5Desc"));
		commands.put("top10", CivSettings.localize.localizedString("cmd_team_top10Desc"));
		commands.put("list", CivSettings.localize.localizedString("cmd_team_listDesc"));
		commands.put("surrender", CivSettings.localize.localizedString("cmd_team_surrenderDesc"));
                commands.put("test", CivSettings.localize.localizedString("cmd_team_test"));
	}
	
	public void surrender_cmd() throws CivException {
		Resident resident = getResident();
		
		if (!resident.hasTeam()) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_surrenderNotInTeam"));
		}
		
		if (!resident.isTeamLeader()) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_NotLeader"));
		}
		
		ArenaTeam team = resident.getTeam();
		Arena arena = team.getCurrentArena();
		
		if (arena == null) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_surrenderNotInMatch"));
		}
		
		ArenaTeam otherTeam = null;
		for (ArenaTeam t : arena.getTeams()) {
			if (t != team) {
				otherTeam = t;
				break;
			}
		}
		
		if (otherTeam == null) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_surrenderNoOpposition"));
		}
		
		ArenaManager.declareVictor(arena, team, otherTeam);
		CivMessage.sendSuccess(sender, CivSettings.localize.localizedString("cmd_team_surrenderSuccess"));

	}
	
	public void arena_cmd() throws CivException {
		Resident resident = getResident();
		
		if (!resident.hasTeam()) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_arenaNotInTeam"));
		}
		
		if (!resident.isTeamLeader()) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_NotLeader"));
		}
		
		ArenaTeam team = resident.getTeam();
		
		if (team.getCurrentArena() != null) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_arenaInArena"));
		}

		for (ArenaTeam t : ArenaManager.teamQueue) {
			if (t == team) {
				ArenaManager.teamQueue.remove(t);
				CivMessage.sendSuccess(sender, CivSettings.localize.localizedString("cmd_team_arenaLeft"));
				return;
			}
		}
		
		ArenaManager.addTeamToQueue(team);
		CivMessage.sendSuccess(sender, CivSettings.localize.localizedString("cmd_team_arenaAdded"));
	}
	
	
	public void list_cmd() {
		CivMessage.sendHeading(sender, CivSettings.localize.localizedString("cmd_team_ListHeading"));
		String out = "";
		
		for (ArenaTeam team : ArenaTeam.arenaTeams.values()) {
			out += team.getName()+", ";
		}
		
		CivMessage.send(sender, out);
	}
	
	
	public void top5_cmd() {
		CivMessage.sendHeading(sender, CivSettings.localize.localizedString("cmd_team_top5Heading"));
		
		for (int i = 0; ((i < 5) && (i < ArenaTeam.teamRankings.size())); i++) {
			ArenaTeam team = ArenaTeam.teamRankings.get(i);
			CivMessage.send(sender, CivColor.Green+team.getName()+": "+CivColor.LightGreen+team.getLadderPoints());
                        if ()
		}
	}

        public void test_cmd() {
		CivMessage.sendHeading(sender, CivSettings.localize.localizedString("cmd_team_test"));
                       if (resident.getName() != BlueBlueBlue) {
                           CivMessage.send(sender, CivColor.Green+resident.getName()+": "+CivColor.LightGreen+test.sucuseffly());
		       } else {
                         private void addCivCraftItemToInventory(String id, Inventory inv) {
		LoreCraftableMaterial craftMat = LoreCraftableMaterial.getCraftMaterialFromId(id);
		ItemStack stack = LoreCraftableMaterial.spawn(craftMat);
		stack = LoreCraftableMaterial.addEnhancement(stack, LoreEnhancement.enhancements.get("LoreEnhancementSoulBound"));
		inv.addItem(stack);
	}
	
	private void addItemToInventory(Material mat, Inventory inv, int amount) {
		ItemStack stack = ItemManager.createItemStack(ItemManager.getId(mat), amount);
		stack = LoreCraftableMaterial.addEnhancement(stack, LoreEnhancement.enhancements.get("LoreEnhancementSoulBound"));
		inv.addItem(stack);
	}
	
	private void createInventory(Resident resident) {
		Player player;
		try {
			player = CivGlobal.getPlayer(resident);
			Inventory inv = Bukkit.createInventory(player, 9*6, resident.getName()+"'s Gear");

			for (int i = 0; i < 3; i++) {
				addCivCraftItemToInventory("mat_tungsten_sword", inv);
				addCivCraftItemToInventory("mat_tungsten_boots", inv);
				addCivCraftItemToInventory("mat_tungsten_chestplate", inv);
				addCivCraftItemToInventory("mat_tungsten_leggings", inv);
				addCivCraftItemToInventory("mat_tungsten_helmet", inv);
		
				addCivCraftItemToInventory("mat_marksmen_bow", inv);
				addCivCraftItemToInventory("mat_composite_leather_boots", inv);
				addCivCraftItemToInventory("mat_composite_leather_chestplate", inv);
				addCivCraftItemToInventory("mat_composite_leather_leggings", inv);
				addCivCraftItemToInventory("mat_composite_leather_helmet", inv);
			}
						
			addCivCraftItemToInventory("mat_vanilla_diamond_pickaxe", inv);

			addItemToInventory(Material.ARROW, inv, 1);
			addItemToInventory(Material.ARROW, inv, 4);
			addItemToInventory(Material.ARROW, inv, 8);
			addItemToInventory(Material.ARROW, inv, 8);
			addItemToInventory(Material.GOLDEN_CARROT, inv, 1);
			addItemToInventory(Material.GOLDEN_CARROT, inv, 3);
                        addItemToInventory(Material.GOLDEN_CARROT, inv, 3);
			addItemToInventory(Material.GOLDEN_CARROT, inv, 7);

			
			playerInvs.put(resident.getName(), inv);
			
		}
		       } 
	}
	
	public void top10_cmd() {
		CivMessage.sendHeading(sender, CivSettings.localize.localizedString("cmd_team_top10Heading"));
		
		for (int i = 0; ((i < 10) && (i < ArenaTeam.teamRankings.size())); i++) {
			ArenaTeam team = ArenaTeam.teamRankings.get(i);
			CivMessage.send(sender, CivColor.Green+team.getName()+": "+CivColor.LightGreen+team.getLadderPoints());
		}
	}
	
	public void printTeamInfo(ArenaTeam team) {
		CivMessage.sendHeading(sender, "Team "+team.getName());
		CivMessage.send(sender, CivColor.Green+CivSettings.localize.localizedString("cmd_team_printPoints")+" "+CivColor.LightGreen+team.getLadderPoints()+
								CivColor.Green+" "+CivSettings.localize.localizedString("Leader")+" "+CivColor.LightGreen+team.getLeader().getName());
		CivMessage.send(sender, CivColor.Green+CivSettings.localize.localizedString("Members")+" "+CivColor.LightGreen+team.getMemberListSaveString());
	}

	public void info_cmd() throws CivException {
		Resident resident = getResident();
		
		if (!resident.hasTeam()) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_arenaNotInTeam"));
		}
		
		ArenaTeam team = resident.getTeam();
		printTeamInfo(team);
	}
	
	public void show_cmd() throws CivException {
		ArenaTeam team = getNamedTeam(1);
		printTeamInfo(team);
	}
	
	public void create_cmd() throws CivException {
		String teamName = getNamedString(1, CivSettings.localize.localizedString("cmd_team_createPrompt"));
		Resident resident = getResident();
		
		if (resident.isProtected()) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_createProtected"));
		}
		
		if (resident.hasTeam()) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_createHasTeam"));
		}
		
		
		ArenaTeam.createTeam(teamName, resident);
		CivMessage.sendSuccess(sender, CivSettings.localize.localizedString("cmd_team_createSuccess"));
	}
	
	public void leave_cmd() throws CivException {
		Resident resident = getResident();
		
		if (!resident.hasTeam()) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_arenaNotInTeam"));
		}
		
		if (resident.isTeamLeader()) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_leaveIsLeader"));
		}
		
		ArenaTeam team = resident.getTeam();
		
		if (team.getCurrentArena() != null) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_arenaInArena"));
		}
		
		ArenaTeam.removeMember(team.getName(), resident);
		CivMessage.sendSuccess(sender, CivSettings.localize.localizedString("var_cmd_team_leaveSuccess",team.getName()));
		CivMessage.sendTeam(team, CivSettings.localize.localizedString("var_cmd_team_leftMessage",resident.getName()));
	}
	
	public void disband_cmd() throws CivException {
		Resident resident = getResident();
		
		if (!resident.isTeamLeader()) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_NotLeader"));
		}
		
		if (resident.getTeam().getCurrentArena() != null) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_arenaInArena"));
		}
		
		String teamName = resident.getTeam().getName();
		ArenaTeam.deleteTeam(teamName);
		ArenaTeam.arenaTeams.remove(teamName);
		CivMessage.sendSuccess(sender, CivSettings.localize.localizedString("var_cmd_team_disbandSuccess",teamName));
	}
	
	public void add_cmd() throws CivException {
		Resident resident = getResident();
		Resident member = getNamedResident(1);
		
		if (!resident.isTeamLeader()) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_NotLeader"));
		}
		
		if (member.hasTeam()) {
			throw new CivException(CivSettings.localize.localizedString("var_cmd_team_addHasTeam",member.getName()));
		}
		
		if (resident.getTeam().getCurrentArena() != null) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_arenaInArena"));
		}
		
		try {
			Player player = CivGlobal.getPlayer(member);
			
			if (member.isProtected()) {
				throw new CivException(CivSettings.localize.localizedString("var_cmd_team_addProtected",player.getName()));
			}
			
			ArenaTeam team = resident.getTeam();
			JoinTeamResponse join = new JoinTeamResponse();
			join.team = team;
			join.resident = member;
			join.sender = (Player)sender;
					
			CivGlobal.questionPlayer(CivGlobal.getPlayer(resident), player, 
					CivSettings.localize.localizedString("var_cmd_team_addRequest",team.getName()),
					30000, join);
			
		} catch (CivException e) {
			throw new CivException(e.getMessage());
		}
				
		CivMessage.sendSuccess(sender, CivSettings.localize.localizedString("var_cmd_team_addInvite",member.getName()));
	}
	
	public void remove_cmd() throws CivException {
		Resident resident = getResident();
		Resident member = getNamedResident(1);
		
		if (!resident.isTeamLeader()) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_NotLeader"));
		}
		
		if (resident.getTeam().getCurrentArena() != null) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_arenaInArena"));
		}
		
		ArenaTeam.removeMember(resident.getTeam().getName(), member);
		CivMessage.sendSuccess(sender, CivSettings.localize.localizedString("var_cmd_team_removeSuccess",member.getName()));
		CivMessage.sendTeam(resident.getTeam(), CivSettings.localize.localizedString("var_cmd_team_leftMessage",member.getName()));

	}
	
	public void changeleader_cmd() throws CivException {
		Resident resident = getResident();
		Resident member = getNamedResident(1);
		
		if (!resident.isTeamLeader()) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_NotLeader"));
		}
		
		ArenaTeam team = resident.getTeam();
		
		if (team.getCurrentArena() != null) {
			throw new CivException(CivSettings.localize.localizedString("cmd_team_arenaInArena"));
		}
		
		if (!team.hasMember(member)) {
			throw new CivException(CivSettings.localize.localizedString("var_cmd_team_changeleaderNotInTeam",member.getName()));
		}
		
		team.setLeader(member);
		team.save();
		
		CivMessage.sendSuccess(sender, CivSettings.localize.localizedString("var_cmd_team_changeleaderSuccess1",member.getName()));
		CivMessage.sendSuccess(member, CivSettings.localize.localizedString("var_cmd_team_changeleaderSuccess2",team.getName()));
		CivMessage.sendTeam(team, CivSettings.localize.localizedString("var_cmd_team_changeleaderSuccess3",resident.getName(),member.getName()));
		
	}
	
	@Override
	public void doDefaultAction() throws CivException {
		showHelp();
		
	}

	@Override
	public void showHelp() {
		showBasicHelp();
	}

	@Override
	public void permissionCheck() throws CivException {

	}

	
	
}
