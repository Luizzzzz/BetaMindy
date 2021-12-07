package betamindy.world.blocks.power;

import mindustry.content.*;
import mindustry.entities.*;
import mindustry.type.*;
import mindustry.world.blocks.power.*;
import mindustry.world.meta.*;

public class NuclearCrafter extends NuclearReactor {
    public ItemStack outputItem;
    public Effect craftEffect = Fx.mineBig;

    public NuclearCrafter(String name){
        super(name);
        lightRadius = 125f;
    }

    @Override
    public void setStats(){
        super.setStats();

        if(outputItem != null){
            stats.add(Stat.output, StatValues.items(itemDuration, outputItem));
        }
    }

    @Override
    public boolean outputsItems(){
        return outputItem != null;
    }

    public class NuclearCrafterBuild extends NuclearReactorBuild {
        @Override
        public void consume(){
            super.consume();

            if(outputItem != null && hasItems){
                for(int i = 0; i < outputItem.amount; i++){
                    offload(outputItem.item);
                }

                craftEffect.at(x, y, outputItem.item.color);
            }
        }

        @Override
        public boolean shouldConsume(){
            if(outputItem != null && hasItems){
                if(items.get(outputItem.item) + outputItem.amount > itemCapacity){
                    return false;
                }
            }
            return super.shouldConsume();
        }

        @Override
        public void updateTile(){
            super.updateTile();
            dumpOutputs();
        }

        public void dumpOutputs(){
            if(hasItems && outputItem != null && timer(timerDump, dumpTime / timeScale)){
                dump(outputItem.item);
            }
        }
    }
}
