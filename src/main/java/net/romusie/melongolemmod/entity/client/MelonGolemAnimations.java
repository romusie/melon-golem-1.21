package net.romusie.melongolemmod.entity.client;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class  MelonGolemAnimations {
        public static final Animation ANIM_MELONGOLEM_IDLE = Animation.Builder.create(0.0F)
                .addBoneAnimation("pumpkin2", new Transformation(Transformation.Targets.SCALE,
                        new Keyframe(0.0F, AnimationHelper.createScalingVector(0.9F, 0.7F, 0.8F), Transformation.Interpolations.LINEAR)
                ))
                .build();

        public static final Animation ANIM_MELONGOLEM_WALK = Animation.Builder.create(0.0F)
                .addBoneAnimation("pumpkin2", new Transformation(Transformation.Targets.SCALE,
                        new Keyframe(0.0F, AnimationHelper.createScalingVector(0.9F, 0.8F, 0.7F), Transformation.Interpolations.LINEAR)
                ))
                .build();

        public static final Animation ANIM_MELONGOLEM_ATTACK = Animation.Builder.create(0.0F)
                .addBoneAnimation("pumpkin", new Transformation(Transformation.Targets.SCALE,
                        new Keyframe(0.0F, AnimationHelper.createScalingVector(0.8F, 0.9F, 0.9F), Transformation.Interpolations.LINEAR)
                ))
                .build();
}