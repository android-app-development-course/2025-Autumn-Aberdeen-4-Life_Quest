package com.example.gameui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameui.model.Equipment
import com.example.gameui.model.ScheduleCategory
import com.example.gameui.model.ScheduleItem
import com.example.gameui.ui.theme.GameColors
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin


data class AttributeData(
    val name: String,
    val value: Int,
    val color: Color
)

data class TaskData(
    val id: Int,
    val title: String,
    val description: String,
    val duration: String,
    val expReward: Int,
    val starReward: Int,
    val attrReward: String,
    val attrValue: Int,
    val difficulty: String,
    val isCompleted: Boolean = false,
    val category: String
)

data class GameState(
    val stars: Int = 100,
    val level: Int = 12,
    val experience: Int = 2450,
    val maxExperience: Int = 3000,
    val physicalAttr: Int = 75,
    val intelligenceAttr: Int = 85,
    val cultivationAttr: Int = 60,
    val tasks: List<TaskData> = emptyList(),
    val bonusClaimedCount: Int = 0,
    val username: String = "冒险者 · 111111",
    val bio: String = "这个人很懒，什么都没有留下",
    val ownedEquipment: List<Int> = emptyList()
)

enum class Screen {
    Main,
    LoadingToTasks,
    LoadingToProfile,
    LoadingToMain,
    LoadingToSchedule,
    LoadingToShop,
    TaskList,
    Profile,
    EditProfile,
    NotificationSettings,
    PrivacySettings,
    Help,
    About,
    Schedule,
    Shop
}

// ==================== 工具函数 ====================

fun getExpByDifficulty(difficulty: String): Int {
    return when (difficulty) {
        "简单" -> 100
        "中等" -> 180
        "困难" -> 250
        else -> 100
    }
}

fun getStarsByDifficulty(difficulty: String): Int {
    return when (difficulty) {
        "简单" -> 10
        "中等" -> 30
        "困难" -> 50
        else -> 10
    }
}

// ==================== 主屏幕 ====================

@Composable
fun GameMainScreen() {
    var currentScreen by remember { mutableStateOf(Screen.Main) }

    // 日程列表
    val scheduleList = remember { mutableStateListOf<ScheduleItem>() }

    var gameState by remember {
        mutableStateOf(
            GameState(
                stars = 100,
                level = 12,
                experience = 2450,
                maxExperience = 3000,
                physicalAttr = 75,
                intelligenceAttr = 85,
                cultivationAttr = 60,
                bonusClaimedCount = 0,
                username = "冒险者 · 111111",
                bio = "这个人很懒，什么都没有留下",
                ownedEquipment = emptyList(),
                tasks = listOf(
                    TaskData(1, "晨跑锻炼", "完成3公里晨跑训练", "30分钟", 100, 10, "体质", 5, "简单", false, "体质"),
                    TaskData(2, "俯卧撑挑战", "完成50个标准俯卧撑", "20分钟", 100, 10, "体质", 6, "简单", false, "体质"),
                    TaskData(3, "游泳训练", "完成800米游泳", "45分钟", 180, 30, "体质", 10, "中等", false, "体质"),
                    TaskData(4, "力量训练", "完成全套哑铃训练", "1小时", 180, 30, "体质", 12, "中等", false, "体质"),
                    TaskData(5, "马拉松备战", "完成10公里长跑", "1. 5小时", 250, 50, "体质", 18, "困难", false, "体质"),
                    TaskData(6, "极限挑战", "完成100个波比跳", "40分钟", 250, 50, "体质", 20, "困难", false, "体质"),
                    TaskData(7, "瑜伽拉伸", "完成30分钟瑜伽课程", "30分钟", 100, 10, "体质", 5, "简单", false, "体质"),
                    TaskData(8, "爬山远足", "完成5公里山地徒步", "2小时", 180, 30, "体质", 15, "中等", false, "体质"),
                    TaskData(9, "英语单词", "背诵50个新单词", "30分钟", 100, 10, "智力", 5, "简单", false, "智力"),
                    TaskData(10, "数学练习", "完成20道数学题", "40分钟", 100, 10, "智力", 6, "简单", false, "智力"),
                    TaskData(11, "编程学习", "完成一个编程小项目", "1.5小时", 180, 30, "智力", 12, "中等", false, "智力"),
                    TaskData(12, "阅读书籍", "阅读专业书籍50页", "1小时", 180, 30, "智力", 10, "中等", false, "智力"),
                    TaskData(13, "逻辑谜题", "完成5道高难度逻辑题", "1小时", 250, 50, "智力", 18, "困难", false, "智力"),
                    TaskData(14, "论文研读", "精读一篇学术论文", "2小时", 250, 50, "智力", 20, "困难", false, "智力"),
                    TaskData(15, "知识复习", "复习今日所学内容", "30分钟", 100, 10, "智力", 5, "简单", false, "智力"),
                    TaskData(16, "技能实践", "完成一次技能实操练习", "1小时", 180, 30, "智力", 12, "中等", false, "智力"),
                    TaskData(17, "冥想静心", "完成15分钟冥想", "15分钟", 100, 10, "修养", 5, "简单", false, "修养"),
                    TaskData(18, "整理房间", "整理并打扫房间", "30分钟", 100, 10, "修养", 6, "简单", false, "修养"),
                    TaskData(19, "书法练习", "练习毛笔字30分钟", "30分钟", 180, 30, "修养", 10, "中等", false, "修养"),
                    TaskData(20, "乐器演奏", "练习乐器45分钟", "45分钟", 180, 30, "修养", 12, "中等", false, "修养"),
                    TaskData(21, "绘画创作", "完成一幅完整画作", "2小时", 250, 50, "修养", 18, "困难", false, "修养"),
                    TaskData(22, "志愿服务", "参与社区志愿活动", "3小时", 250, 50, "修养", 20, "困难", false, "修养"),
                    TaskData(23, "感恩日记", "写下今日感恩的事", "15分钟", 100, 10, "修养", 5, "简单", false, "修养"),
                    TaskData(24, "茶道修习", "完成一次茶道练习", "1小时", 180, 30, "修养", 12, "中等", false, "修养")
                )
            )
        )
    }

    // 任务完成回调
    val onTaskComplete:  (TaskData) -> Unit = { task ->
        if (! task.isCompleted) {
            val expGain = getExpByDifficulty(task.difficulty)
            val starGain = getStarsByDifficulty(task.difficulty)

            var newExp = gameState.experience + expGain
            var newLevel = gameState.level
            var newMaxExp = gameState.maxExperience

            while (newExp >= newMaxExp) {
                newExp -= newMaxExp
                newLevel += 1
                newMaxExp = 3000 + (newLevel - 12) * 500
            }

            val newCompletedCount = gameState.tasks.count { it.isCompleted } + 1
            val previousBonusCount = gameState.bonusClaimedCount
            val newBonusCount = newCompletedCount / 5
            val bonusStars = (newBonusCount - previousBonusCount) * 300

            gameState = gameState.copy(
                stars = gameState.stars + starGain + bonusStars,
                level = newLevel,
                experience = newExp,
                maxExperience = newMaxExp,
                physicalAttr = if (task.category == "体质") gameState.physicalAttr + task.attrValue else gameState. physicalAttr,
                intelligenceAttr = if (task.category == "智力") gameState.intelligenceAttr + task.attrValue else gameState.intelligenceAttr,
                cultivationAttr = if (task.category == "修养") gameState.cultivationAttr + task.attrValue else gameState.cultivationAttr,
                bonusClaimedCount = newBonusCount,
                tasks = gameState.tasks.map {
                    if (it.id == task.id) it.copy(isCompleted = true) else it
                }
            )
        }
    }

    // 更新用户资料回调
    val onUpdateProfile: (String, String) -> Unit = { newUsername, newBio ->
        gameState = gameState.copy(
            username = newUsername,
            bio = newBio
        )
    }

    // 购买装备回调
    val onPurchaseEquipment: (Equipment) -> Unit = { equipment ->
        if (gameState.stars >= equipment.price && !gameState.ownedEquipment.contains(equipment.id)) {
            gameState = gameState.copy(
                stars = gameState.stars - equipment.price,
                ownedEquipment = gameState.ownedEquipment + equipment.id
            )
        }
    }

    when (currentScreen) {
        Screen.Main -> {
            MainContent(
                gameState = gameState,
                onNavigateToTasks = { currentScreen = Screen. LoadingToTasks },
                onNavigateToProfile = { currentScreen = Screen.LoadingToProfile },
                onNavigateToSchedule = { currentScreen = Screen.LoadingToSchedule },
                onNavigateToShop = { currentScreen = Screen. LoadingToShop }
            )
        }
        Screen.LoadingToTasks -> {
            LoadingScreen(loadingText = "正在加载任务... ") {
                currentScreen = Screen.TaskList
            }
        }
        Screen. LoadingToProfile -> {
            LoadingScreen(loadingText = "正在加载个人中心...") {
                currentScreen = Screen.Profile
            }
        }
        Screen.LoadingToMain -> {
            LoadingScreen(loadingText = "正在返回主页... ") {
                currentScreen = Screen.Main
            }
        }
        Screen.LoadingToSchedule -> {
            LoadingScreen(loadingText = "正在加载日程...") {
                currentScreen = Screen.Schedule
            }
        }
        Screen.LoadingToShop -> {
            LoadingScreen(loadingText = "正在加载商城...") {
                currentScreen = Screen.Shop
            }
        }
        Screen.TaskList -> {
            TaskListScreen(
                gameState = gameState,
                onBackClick = { currentScreen = Screen.LoadingToMain },
                onTaskComplete = onTaskComplete
            )
        }
        Screen.Profile -> {
            ProfileScreen(
                gameState = gameState,
                onBackClick = { currentScreen = Screen.LoadingToMain },
                onNavigateToEdit = { currentScreen = Screen.EditProfile },
                onNavigateToNotification = { currentScreen = Screen.NotificationSettings },
                onNavigateToPrivacy = { currentScreen = Screen. PrivacySettings },
                onNavigateToHelp = { currentScreen = Screen.Help },
                onNavigateToAbout = { currentScreen = Screen.About }
            )
        }
        Screen.EditProfile -> {
            EditProfileScreen(
                username = gameState.username,
                bio = gameState.bio,
                onBackClick = { currentScreen = Screen.Profile },
                onSave = { newUsername, newBio ->
                    onUpdateProfile(newUsername, newBio)
                    currentScreen = Screen.Profile
                }
            )
        }
        Screen. NotificationSettings -> {
            NotificationSettingsScreen(
                onBackClick = { currentScreen = Screen.Profile }
            )
        }
        Screen.PrivacySettings -> {
            PrivacySettingsScreen(
                onBackClick = { currentScreen = Screen.Profile }
            )
        }
        Screen.Help -> {
            HelpScreen(
                onBackClick = { currentScreen = Screen. Profile }
            )
        }
        Screen.About -> {
            AboutScreen(
                onBackClick = { currentScreen = Screen. Profile }
            )
        }
        Screen.Schedule -> {
            UnifiedScheduleScreen(
                scheduleList = scheduleList. toList(),
                onScheduleListChange = { newList ->
                    scheduleList. clear()
                    scheduleList.addAll(newList)
                },
                onBackClick = { currentScreen = Screen.LoadingToMain }
            )
        }
        Screen.Shop -> {
            UnifiedShopScreen(
                stars = gameState.stars,
                ownedEquipment = gameState.ownedEquipment,
                onPurchase = onPurchaseEquipment,
                onBackClick = { currentScreen = Screen.LoadingToMain }
            )
        }
    }
}

// ==================== 加载界面 ====================

@Composable
fun LoadingScreen(
    loadingText: String = "正在加载...",
    onLoadingComplete: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val dot1Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot1"
    )

    val dot2Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot2"
    )

    val dot3Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot3"
    )

    LaunchedEffect(Unit) {
        delay(1500)
        onLoadingComplete()
    }

    Box(
        modifier = Modifier
            . fillMaxSize()
            .background(GameColors.BackgroundLight),
        contentAlignment = Alignment. Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier. size(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 4. dp.toPx()
                    val radius = (size.minDimension - strokeWidth) / 2
                    val center = Offset(size.width / 2, size.height / 2)

                    drawCircle(
                        color = GameColors.BorderLight,
                        radius = radius,
                        center = center,
                        style = Stroke(width = strokeWidth)
                    )

                    drawArc(
                        color = GameColors.AccentPurple,
                        startAngle = rotation,
                        sweepAngle = 120f,
                        useCenter = false,
                        topLeft = Offset(center.x - radius, center.y - radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )

                    drawArc(
                        color = GameColors.AccentPink,
                        startAngle = rotation + 140f,
                        sweepAngle = 80f,
                        useCenter = false,
                        topLeft = Offset(center.x - radius, center.y - radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )

                    drawArc(
                        color = GameColors.AccentBlue,
                        startAngle = rotation + 240f,
                        sweepAngle = 60f,
                        useCenter = false,
                        topLeft = Offset(center.x - radius, center.y - radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(width = strokeWidth, cap = StrokeCap. Round)
                    )
                }
            }

            Spacer(modifier = Modifier. height(30.dp))

            Row(horizontalArrangement = Arrangement. spacedBy(8.dp)) {
                Canvas(modifier = Modifier.size(12.dp)) {
                    drawCircle(color = GameColors.AccentPink. copy(alpha = dot1Alpha))
                }
                Canvas(modifier = Modifier.size(12.dp)) {
                    drawCircle(color = GameColors.AccentPurple.copy(alpha = dot2Alpha))
                }
                Canvas(modifier = Modifier.size(12.dp)) {
                    drawCircle(color = GameColors.AccentBlue.copy(alpha = dot3Alpha))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = loadingText,
                color = GameColors.TextSecondary,
                fontSize = 16.sp,
                fontWeight = FontWeight. Medium
            )
        }
    }
}

// ==================== 主页内容 ====================

@Composable
private fun MainContent(
    gameState: GameState,
    onNavigateToTasks: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToSchedule:  () -> Unit,
    onNavigateToShop: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()

    val attributes = listOf(
        AttributeData("体质", gameState.physicalAttr, GameColors.AttrPhysical),
        AttributeData("智力", gameState.intelligenceAttr, GameColors.AccentBlue),
        AttributeData("修养", gameState. cultivationAttr, GameColors. AccentOrange)
    )

    Column(
        modifier = Modifier
            . fillMaxSize()
            .background(GameColors.BackgroundLight)
            .verticalScroll(scrollState)
    ) {
        HeaderSection(
            level = gameState.level,
            experience = gameState.experience,
            maxExperience = gameState.maxExperience,
            username = gameState.username,
            onAvatarClick = onNavigateToProfile
        )
        Spacer(modifier = Modifier. height(20.dp))
        ResourceCardsSection(
            stars = gameState.stars,
            equipmentCount = gameState.ownedEquipment.size
        )
        Spacer(modifier = Modifier.height(24.dp))
        CharacterSectionWithRing(attributes = attributes)
        Spacer(modifier = Modifier.height(24.dp))
        NavigationSection(
            selectedTab = selectedTab,
            onTabSelected = { index ->
                selectedTab = index
                when (index) {
                    0 -> onNavigateToTasks()
                    1 -> onNavigateToSchedule()
                    2 -> onNavigateToShop()
                }
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}

// ==================== 角色属性环形图 ====================

@Composable
private fun CharacterSectionWithRing(attributes: List<AttributeData>) {
    var startAnimation by remember { mutableStateOf(false) }

    val ringAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
        label = "ring"
    )

    val ringScale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.3f,
        animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
        label = "scale"
    )

    val avatarScale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "avatar"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        SectionTitle(title = "角色属性")
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(GameColors.CardBackground)
                .padding(24.dp),
            contentAlignment = Alignment. Center
        ) {
            Box(
                modifier = Modifier. size(300.dp),
                contentAlignment = Alignment. Center
            ) {
                AnimatedAttributeRingChart(
                    attributes = attributes,
                    animationProgress = ringAnimation,
                    modifier = Modifier.fillMaxSize().scale(ringScale)
                )

                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .scale(avatarScale)
                        .clip(CircleShape)
                        .background(GameColors.BackgroundGray)
                        .border(3.dp, GameColors.BorderLight, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = GameColors.TextMuted,
                        modifier = Modifier. size(50.dp)
                    )
                }

                val total = attributes.sumOf { it.value }
                var currentStartAngle = -90f

                attributes.forEachIndexed { index, attr ->
                    val sweepAngle = (attr.value. toFloat() / total) * 360f
                    val midAngle = currentStartAngle + sweepAngle / 2

                    val labelAlpha by animateFloatAsState(
                        targetValue = if (startAnimation) 1f else 0f,
                        animationSpec = tween(durationMillis = 600, delayMillis = 1200 + index * 200, easing = FastOutSlowInEasing),
                        label = "label$index"
                    )

                    AttributeLabelOnRing(
                        attribute = attr,
                        angle = midAngle,
                        radius = 105f,
                        alpha = labelAlpha
                    )

                    currentStartAngle += sweepAngle
                }
            }
        }
    }
}

@Composable
private fun AnimatedAttributeRingChart(
    attributes: List<AttributeData>,
    animationProgress: Float,
    modifier: Modifier = Modifier
) {
    val total = attributes.sumOf { it.value }

    Canvas(modifier = modifier) {
        val strokeWidth = 55.dp.toPx()
        val radius = (size.minDimension - strokeWidth) / 2
        val center = Offset(size.width / 2, size.height / 2)

        var startAngle = -90f

        attributes.forEach { attr ->
            val sweepAngle = (attr.value. toFloat() / total) * 360f
            val animatedSweep = (sweepAngle - 3f) * animationProgress

            if (animatedSweep > 0) {
                drawArc(
                    color = attr.color,
                    startAngle = startAngle,
                    sweepAngle = animatedSweep,
                    useCenter = false,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(width = strokeWidth, cap = StrokeCap. Butt)
                )
            }
            startAngle += sweepAngle
        }
    }
}

@Composable
private fun BoxScope.AttributeLabelOnRing(
    attribute: AttributeData,
    angle: Float,
    radius: Float,
    alpha: Float
) {
    val angleRad = Math.toRadians(angle. toDouble())
    val x = (radius * cos(angleRad)).toFloat()
    val y = (radius * sin(angleRad)).toFloat()

    Box(
        modifier = Modifier
            .offset(x = x. dp, y = y.dp)
            .align(Alignment.Center)
            .alpha(alpha)
            .scale(0.5f + alpha * 0.5f)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = attribute.name,
                color = GameColors. TextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = attribute.value.toString(),
                color = GameColors.TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ==================== 头部区域 ====================

@Composable
private fun HeaderSection(
    level: Int,
    experience: Int,
    maxExperience: Int,
    username:  String,
    onAvatarClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GameColors.AccentPurple, GameColors.AccentBlue)
                )
            )
            .padding(24.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White. copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons. Outlined.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier. size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = username,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(GameColors.AccentYellow)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "LV.  $level",
                            color = GameColors.TextPrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .border(3.dp, Color.White, CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                        .clickable { onAvatarClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "个人中心",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement. SpaceBetween
                ) {
                    Text(text = "经验值", color = Color.White. copy(alpha = 0.9f), fontSize = 13.sp)
                    Text(
                        text = "$experience / $maxExperience",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier. height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(experience. toFloat() / maxExperience)
                            .clip(RoundedCornerShape(5.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(GameColors.AccentYellow, GameColors.AccentOrange)
                                )
                            )
                    )
                }
            }
        }
    }
}

// ==================== 资源卡片 ====================

@Composable
private fun ResourceCardsSection(stars: Int, equipmentCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ResourceCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.Star,
            iconColor = GameColors.AccentYellow,
            label = "星星",
            value = stars. toString()
        )

        ResourceCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default. Shield,
            iconColor = GameColors.AccentPink,
            label = "装备",
            value = equipmentCount.toString()
        )
    }
}

@Composable
private fun ResourceCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconColor: Color,
    label: String,
    value: String
) {
    Row(
        modifier = modifier
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(GameColors.CardBackground)
            .padding(16.dp),
        verticalAlignment = Alignment. CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment. Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(text = label, color = GameColors.TextSecondary, fontSize = 13.sp)
            Text(
                text = value,
                color = GameColors.TextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ==================== 底部导航 ====================

@Composable
private fun NavigationSection(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement. spacedBy(12.dp)
    ) {
        NavItem(
            icon = Icons. Outlined.Assignment,
            label = "每日任务",
            isSelected = selectedTab == 0,
            selectedColor = GameColors.AccentPurple,
            onClick = { onTabSelected(0) },
            modifier = Modifier.weight(1f)
        )

        NavItem(
            icon = Icons. Outlined.CalendarToday,
            label = "日程规划",
            isSelected = selectedTab == 1,
            selectedColor = GameColors.AccentBlue,
            onClick = { onTabSelected(1) },
            modifier = Modifier.weight(1f)
        )

        NavItem(
            icon = Icons. Outlined.ShoppingCart,
            label = "系统商城",
            isSelected = selectedTab == 2,
            selectedColor = GameColors.AccentGreen,
            onClick = { onTabSelected(2) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun NavItem(
    icon: ImageVector,
    label:  String,
    isSelected: Boolean,
    selectedColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .shadow(if (! isSelected) 2.dp else 0.dp, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(14.dp))
            .background(
                if (isSelected) selectedColor.copy(alpha = 0.1f)
                else GameColors.CardBackground
            )
            .then(
                if (isSelected) Modifier.border(1.5.dp, selectedColor, RoundedCornerShape(14.dp))
                else Modifier
            )
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) selectedColor else GameColors.TextMuted,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier. height(4.dp))
        Text(
            text = label,
            color = if (isSelected) selectedColor else GameColors.TextMuted,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

@Composable
private fun SectionTitle(title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(20.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(GameColors.AccentPurple)
        )
        Spacer(modifier = Modifier. width(10.dp))
        Text(
            text = title,
            color = GameColors.TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ==================== 统一风格的日程规划界面 ====================

@Composable
fun UnifiedScheduleScreen(
    scheduleList: List<ScheduleItem>,
    onScheduleListChange: (List<ScheduleItem>) -> Unit,
    onBackClick: () -> Unit
) {
    val weekDays = listOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
    var showDialog by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<ScheduleItem?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GameColors.BackgroundLight)
    ) {
        // 顶部栏
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(GameColors.AccentPurple, GameColors.AccentBlue)
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "返回",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "日程规划",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier. weight(1f)
                )

                // 添加按钮
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        . background(Color.White.copy(alpha = 0.2f))
                        .clickable { editingItem = null; showDialog = true }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "添加", color = Color.White, fontSize = 14.sp)
                    }
                }
            }
        }

        // 日程列表
        LazyColumn(
            modifier = Modifier. fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(weekDays.size) { dayIndex ->
                val daySchedules = scheduleList.filter { it.dayOfWeek == dayIndex }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        . background(GameColors.CardBackground)
                        .padding(16.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    . clip(RoundedCornerShape(8.dp))
                                    .background(GameColors.AccentBlue. copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons. Outlined.CalendarToday,
                                    contentDescription = null,
                                    tint = GameColors.AccentBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = weekDays[dayIndex],
                                color = GameColors.TextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            if (daySchedules.isNotEmpty()) {
                                Spacer(modifier = Modifier. width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(GameColors.AccentBlue)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "${daySchedules.size}",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (daySchedules.isEmpty()) {
                            Text(
                                text = "暂无日程安排",
                                color = GameColors.TextMuted,
                                fontSize = 14.sp
                            )
                        } else {
                            daySchedules.forEach { item ->
                                UnifiedScheduleItemCard(
                                    item = item,
                                    onEdit = { editingItem = it; showDialog = true },
                                    onDelete = { onScheduleListChange(scheduleList.filter { s -> s.id != it.id }) }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        ScheduleItemDialog(
            initialItem = editingItem,
            onDismiss = { showDialog = false },
            onConfirm = { item ->
                if (scheduleList.any { it.id == item.id }) {
                    onScheduleListChange(scheduleList.map { if (it.id == item.id) item else it })
                } else {
                    onScheduleListChange(scheduleList + item)
                }
                showDialog = false
            }
        )
    }
}

@Composable
private fun UnifiedScheduleItemCard(
    item: ScheduleItem,
    onEdit: (ScheduleItem) -> Unit,
    onDelete: (ScheduleItem) -> Unit
) {
    val categoryColor = when (item.category) {
        ScheduleCategory.PHYSIQUE -> GameColors.AttrPhysical
        ScheduleCategory. CULTIVATION -> GameColors.AccentOrange
        ScheduleCategory. INTELLIGENCE -> GameColors.AccentBlue
        ScheduleCategory.OTHER -> GameColors.TextMuted
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(categoryColor.copy(alpha = 0.1f))
            .border(1.dp, categoryColor. copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment. CenterVertically) {
                    Text(
                        text = item. title,
                        color = GameColors.TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(categoryColor)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = item.category. displayName,
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.desc,
                    color = GameColors.TextSecondary,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons. Outlined.Schedule,
                        contentDescription = null,
                        tint = GameColors.TextMuted,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${item.startTime} - ${item.endTime}",
                        color = GameColors.TextMuted,
                        fontSize = 12.sp
                    )
                }
            }

            IconButton(onClick = { onEdit(item) }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "编辑",
                    tint = GameColors.AccentBlue,
                    modifier = Modifier. size(20.dp)
                )
            }
            IconButton(onClick = { onDelete(item) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "删除",
                    tint = GameColors.AccentPink,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// ==================== 统一风格的商城界面 ====================

@Composable
fun UnifiedShopScreen(
    stars: Int,
    ownedEquipment: List<Int>,
    onPurchase: (Equipment) -> Unit,
    onBackClick: () -> Unit
) {
    val equipmentList = remember {
        listOf(
            Equipment(1, "力量护腕", "增强体质训练效果，使体质属性提升速度+20%", 50, "physique", 20),
            Equipment(2, "修炼宝典", "提升修养训练效果，使修养属性提升速度+20%", 50, "cultivation", 20),
            Equipment(3, "智慧之眼", "增强智力训练效果，使智力属性提升速度+20%", 50, "intelligence", 20),
            Equipment(4, "传奇战甲", "大幅提升体质训练效果，使体质属性提升速度+50%", 150, "physique", 50),
            Equipment(5, "仙道秘籍", "大幅提升修养训练效果，使修养属性提升速度+50%", 150, "cultivation", 50),
            Equipment(6, "圣者之冠", "大幅提升智力训练效果，使智力属性提升速度+50%", 150, "intelligence", 50)
        )
    }

    var selectedEquipment by remember { mutableStateOf<Equipment?>(null) }
    var showConfirm by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GameColors.BackgroundLight)
    ) {
        // 顶部栏
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(GameColors.AccentPurple, GameColors.AccentBlue)
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "返回",
                        tint = Color.White,
                        modifier = Modifier. size(24.dp)
                    )
                }

                Spacer(modifier = Modifier. width(16.dp))

                Text(
                    text = "系统商城",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight. Bold,
                    modifier = Modifier.weight(1f)
                )

                // 星星数量
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = GameColors.AccentYellow,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stars.toString(),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // 说明卡片
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(2.dp, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(GameColors. CardBackground)
                .border(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(
                        colors = listOf(GameColors.AccentPurple. copy(alpha = 0.5f), GameColors.AccentPink.copy(alpha = 0.5f))
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(GameColors.AccentYellow.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CardGiftcard,
                        contentDescription = null,
                        tint = GameColors.AccentYellow,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "装备商城",
                        color = GameColors.TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier. height(4.dp))
                    Text(
                        text = "使用星星兑换装备，提升属性训练效果！",
                        color = GameColors.TextSecondary,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // 装备列表
        LazyColumn(
            modifier = Modifier. fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(equipmentList) { equipment ->
                UnifiedShopCard(
                    equipment = equipment,
                    owned = ownedEquipment. contains(equipment.id),
                    affordable = stars >= equipment.price,
                    onClick = {
                        selectedEquipment = equipment
                        showConfirm = true
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }

    // 确认对话框
    if (showConfirm && selectedEquipment != null) {
        val eq = selectedEquipment!!
        AlertDialog(
            onDismissRequest = { showConfirm = false; selectedEquipment = null },
            title = {
                Text("确认兑换", color = GameColors.TextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Column {
                    Text(eq.name, fontWeight = FontWeight.Bold, color = GameColors.TextPrimary)
                    Spacer(modifier = Modifier. height(8.dp))
                    Text(eq.description, color = GameColors.TextSecondary, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = GameColors.AccentYellow,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${eq.price} 星星",
                            color = GameColors.AccentYellow,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (ownedEquipment.contains(eq.id)) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("你已拥有该装备", color = GameColors. AccentGreen)
                    } else if (stars < eq.price) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("星星不足，无法兑换", color = GameColors.AccentPink)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (! ownedEquipment.contains(eq.id) && stars >= eq.price) {
                            onPurchase(eq)
                        }
                        showConfirm = false
                        selectedEquipment = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GameColors.AccentPurple),
                    enabled = ! ownedEquipment.contains(eq.id) && stars >= eq.price
                ) {
                    Text("确认兑换")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirm = false; selectedEquipment = null }) {
                    Text("取消", color = GameColors.TextSecondary)
                }
            },
            containerColor = GameColors.CardBackground
        )
    }
}

@Composable
private fun UnifiedShopCard(
    equipment: Equipment,
    owned: Boolean,
    affordable: Boolean,
    onClick: () -> Unit
) {
    val typeColor = when (equipment.type) {
        "physique" -> GameColors.AttrPhysical
        "cultivation" -> GameColors.AccentOrange
        "intelligence" -> GameColors.AccentBlue
        else -> GameColors.TextMuted
    }

    val typeName = when (equipment.type) {
        "physique" -> "体质"
        "cultivation" -> "修养"
        "intelligence" -> "智力"
        else -> equipment.type
    }

    val typeIcon = when (equipment.type) {
        "physique" -> Icons.Default.FitnessCenter
        "cultivation" -> Icons.Default.Spa
        "intelligence" -> Icons. Default.Psychology
        else -> Icons.Default. Shield
    }

    Box(
        modifier = Modifier
            . fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(GameColors. CardBackground)
            .then(
                if (! owned) Modifier.border(1.dp, typeColor. copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                else Modifier
            )
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // 图标
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(typeColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment. Center
            ) {
                Icon(
                    imageVector = typeIcon,
                    contentDescription = null,
                    tint = typeColor,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier. width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment. CenterVertically) {
                    Text(
                        text = equipment.name,
                        color = if (owned) GameColors.TextMuted else GameColors.TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(typeColor. copy(alpha = if (owned) 0.3f else 1f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = typeName,
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = equipment.description,
                    color = GameColors.TextSecondary,
                    fontSize = 12.sp,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = GameColors.AccentYellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${equipment.price}",
                        color = GameColors. AccentYellow,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier. width(12.dp))

            // 购买按钮
            val buttonText = when {
                owned -> "已拥有"
                affordable -> "兑换"
                else -> "不足"
            }

            Button(
                onClick = onClick,
                enabled = ! owned && affordable,
                colors = ButtonDefaults.buttonColors(
                    containerColor = when {
                        owned -> GameColors.AccentGreen
                        affordable -> GameColors.AccentPurple
                        else -> GameColors.TextMuted
                    },
                    disabledContainerColor = if (owned) GameColors.AccentGreen. copy(alpha = 0.6f) else GameColors.TextMuted. copy(alpha = 0.5f)
                ),
                modifier = Modifier.height(36.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = buttonText,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}