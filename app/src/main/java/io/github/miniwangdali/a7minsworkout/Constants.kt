package io.github.miniwangdali.a7minsworkout

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel> {
        return arrayListOf(
            ExerciseModel(
                1,
                "Jumping Jacks",
                R.drawable.ic_jumping_jacks
            ),
            ExerciseModel(
                2,
                "Abdominal Crunch",
                R.drawable.ic_abdominal_crunch
            ),
            ExerciseModel(
                3,
                "High Knees Running In Place",
                R.drawable.ic_high_knees_running_in_place
            ),
            ExerciseModel(
                4,
                "Lunge",
                R.drawable.ic_lunge
            ),
            ExerciseModel(
                5,
                "Plank",
                R.drawable.ic_plank
            ),
            ExerciseModel(
                6,
                "Push Up",
                R.drawable.ic_push_up
            ),
            ExerciseModel(
                7,
                "Push Up And Rotation",
                R.drawable.ic_push_up_and_rotation
            ),
            ExerciseModel(
                8,
                "Side Plank",
                R.drawable.ic_side_plank
            ),
            ExerciseModel(
                9,
                "Squat",
                R.drawable.ic_squat
            ),
            ExerciseModel(
                10,
                "Step Up Onto Chair",
                R.drawable.ic_step_up_onto_chair
            ),
            ExerciseModel(
                11,
                "Triceps Dip On Chair",
                R.drawable.ic_triceps_dip_on_chair
            ),
            ExerciseModel(
                12,
                "Wall Sit",
                R.drawable.ic_wall_sit
            )
        )
    }
}