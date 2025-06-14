package net.grishka.testmod.item.custom;

import net.grishka.testmod.util.ModTags;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MetelDetectorItem extends Item {
    public MetelDetectorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        if(!pContext.getLevel().isClientSide()) {
            BlockPos positionclicked = pContext.getClickedPos();
            Player player=pContext.getPlayer();
            boolean foundBlock=false;

            for(int i =0; i <=positionclicked.getY()+64; i++){
                BlockState state=pContext.getLevel().getBlockState(positionclicked.below(i));
                if(isValuableBlock(state)){

                    outputValuableCoords(positionclicked.below(i), player, state.getBlock());

                    foundBlock = true;

                    break;


                }

            }

            if (!foundBlock){
                player.sendSystemMessage(Component.literal("No Iron Ore Found"));
            }

        }

        pContext.getItemInHand().hurtAndBreak(1,pContext.getPlayer(),
        player -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return InteractionResult.SUCCESS;
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents,
                                TooltipFlag pIsAdvanced) {


        if(Screen.hasShiftDown())
            pTooltipComponents.add(Component.literal("Finds Ores by looking in the direction"));
        else
            pTooltipComponents.add(Component.literal("Press Shift to view"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }


    private void outputValuableCoords(BlockPos blockPos, Player player, Block block) {
        player.sendSystemMessage(Component.literal("Found " + I18n.get(block.getDescriptionId()) + " at " +
                "(" + blockPos.getX() + ", " + blockPos.getY() + "," + blockPos.getZ() + ")"));
    }


        private boolean isValuableBlock(BlockState state) {

        return state.is(Blocks.IRON_ORE) || state.is(Blocks.DEEPSLATE_IRON_ORE) || state.is(Blocks.DIAMOND_ORE) || state.is(Blocks.DEEPSLATE_DIAMOND_ORE) || state.is(ModTags.Blocks.METEL_DETECTOR_ORES);
    }
}
