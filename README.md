# 🤖 Droid Battle Arena

## 🤖 AI_GENERATED

Цей README файл створено з використанням штучного інтелекту для докладного опису лабораторної роботи №3.

---

## 🚀 Швидкий старт

### 📦 Інсталяція та запуск

1. **Перевірте Java** (потрібна Java 8+):
   ```bash
   java -version
   ```

2. **Клонуйте репозиторій**:
   ```bash
   git clone https://github.com/ryl1k/pp-lr-3.git
   cd pp-lr-3
   ```

3. **Компіляція**:
   ```bash
   javac -d build -cp src src/main/droids/*.java src/main/battle/*.java src/main/io/*.java src/main/Main.java
   ```

4. **Запуск**:
   ```bash
   java -cp build main.Main
   ```

   **Або на Windows**:
   ```cmd
   run.bat
   ```

### 🎮 Що робить програма

**Droid Battle Arena** - це інтерактивний симулятор боїв дроїдів, де ви можете:

- **Створювати дроїдів** різних типів з унікальними здібностями
- **Проводити дуелі** 1 на 1 або командні битви
- **Зберігати та переглядати** записи боїв
- **Насолоджуватися** кольоровою візуалізацією в терміналі

**Швидкий приклад**: Запустіть програму → Виберіть "1" (Дуель) → Створіть двох дроїдів → Спостерігайте за епічним боєм!

---

## 📋 Опис лабораторної роботи

**Лабораторна робота №3**: Розробка системи бою дроїдів з використанням принципів об'єктно-орієнтованого програмування на Java.

### 🎯 Мета роботи
- Реалізувати систему класів з наслідуванням та поліморфізмом
- Створити інтерактивний бойовий симулятор
- Впровадити систему збереження та відтворення боїв
- Забезпечити зручний користувацький інтерфейс

---

## 🏗️ Архітектура проекту

### 📁 Структура файлів
```
lr_3/
├── src/main/
│   ├── Main.java                    # Головний клас програми
│   ├── droids/                      # Пакет з класами дроїдів
│   │   ├── Droid.java              # Базовий клас дроїда
│   │   ├── AssaultDroid.java       # Штурмовий дроїд
│   │   ├── TankDroid.java          # Танк дроїд
│   │   ├── SniperDroid.java        # Снайперський дроїд
│   │   └── HealerDroid.java        # Лікувальний дроїд
│   ├── battle/                      # Пакет з типами боїв
│   │   ├── Battle.java             # Інтерфейс бою
│   │   ├── OneVsOne.java           # Дуель 1 на 1
│   │   └── TeamBattle.java         # Командний бій
│   └── io/                         # Пакет вводу/виводу
│       ├── BattleSaver.java        # Збереження логів
│       ├── BattleLoader.java       # Завантаження та replay
│       ├── BattleVisualizer.java   # Візуалізація боїв
│       └── DroidFactory.java       # Створення дроїдів
├── build/                          # Скомпільовані класи
├── logs/                           # Логи боїв
└── run.bat                         # Запуск на Windows
```

### 🔗 UML діаграма класів
```
                    ┌─────────────────┐
                    │     Droid       │
                    │   (abstract)    │
                    ├─────────────────┤
                    │ - name: String  │
                    │ - health: int   │
                    │ - damage: int   │
                    │ - armor: int    │
                    ├─────────────────┤
                    │ + takeTurn()    │
                    │ + takeDamage()  │
                    │ + isAlive()     │
                    └─────────────────┘
                            ▲
            ┌───────────────┼───────────────┐
            │               │               │
    ┌───────────────┐ ┌───────────────┐ ┌───────────────┐
    │ AssaultDroid  │ │   TankDroid   │ │ SniperDroid   │
    ├───────────────┤ ├───────────────┤ ├───────────────┤
    │+ takeTurn()   │ │+ takeTurn()   │ │+ takeTurn()   │
    │+ doubleAttack │ │+ armorReduct  │ │+ criticalHit  │
    └───────────────┘ └───────────────┘ └───────────────┘
            │
    ┌───────────────┐
    │ HealerDroid   │
    ├───────────────┤
    │+ takeTurn()   │
    │+ healAlly()   │
    └───────────────┘
```

---

## 🤖 Типи дроїдів та їх характеристики

### ⚡ Assault Droid (Штурмовий)
- **Здоров'я**: 100 HP
- **Урон**: 25 DMG
- **Броня**: 0 ARM
- **Особливість**: 30% шанс подвійної атаки (збільшується при невдачах)
- **Стратегія**: Високий урон, агресивна тактика
- **Ідеально для**: Швидких боїв та знищення слабких цілей

### 🛡️ Tank Droid (Танк)
- **Здоров'я**: 150 HP
- **Урон**: 15 DMG
- **Броня**: 20 ARM (зменшується від пошкоджень)
- **Особливість**: Поглинання урону, висока витривалість
- **Стратегія**: Захисна тактика, довгі бої
- **Ідеально для**: Командних битв як "мішень для урону"

### 🎯 Sniper Droid (Снайпер)
- **Здоров'я**: 50 HP
- **Урон**: 50 DMG
- **Броня**: 0 ARM
- **Особливість**: 30% шанс критичного удару (+20 урону = 70 DMG)
- **Стратегія**: Висока точність, швидке знищення
- **Ідеально для**: Елімінації важливих цілей

### 💚 Healer Droid (Лікар)
- **Здоров'я**: 75 HP
- **Урон**: 10 DMG
- **Лікування**: 25 HP
- **Особливість**: 50% шанс лікування союзника або себе
- **Стратегія**: Підтримка команди, довгострокова гра
- **Ідеально для**: Командних битв як "саппорт"

### ⚖️ Баланс типів
```
Assault > Healer > Sniper > Tank > Assault
  ⚡        💚        🎯      🛡️      ⚡
(швидко)  (лікує)  (критує) (тримає) (атакує)
```

---

## 🎮 Режими гри та функціонал

### 🥊 Режими боїв

#### 1. 🤺 Дуель 1 на 1
```
Droid #1: [ASLT] Destroyer [##########..........] 50/100 (50%)
   VS
Droid #2: [TANK] Guardian  [################....] 120/150 (80%)

>>> ROUND 3 <<<
[ASLT] Destroyer attacks [TANK] Guardian for 25 damage!
[TANK] Guardian's armor absorbs 15 damage!
[TANK] Guardian takes 10 damage! (110/150 HP)
```

#### 2. ⚔️ Командні бої
```
TEAM A: [ASLT] Alpha, [HEAL] Medic
TEAM B: [TANK] Defender, [SNPR] Hunter

>>> ROUND 1 <<<
[HEAL] Medic heals [ASLT] Alpha for 25 HP!
[SNPR] Hunter CRITICAL HIT on [ASLT] Alpha for 70 damage!
[ASLT] Alpha XXX DESTROYED!
```

#### 3. 📼 Система Replay
- Автоматичне знаходження збережених боїв в `logs/`
- Показ часу боїв: `just now`, `5m ago`, `2h ago`
- Різні швидкості відтворення: instant, fast, normal, slow
- Кольорова візуалізація подій

### 🛠️ Створення дроїдів

#### Готові шаблони:
1. **Assault Template**: "Destroyer" з стандартними характеристиками
2. **Tank Template**: "Guardian" з максимальною бронею
3. **Sniper Template**: "Hunter" з високою точністю
4. **Healer Template**: "Medic" з лікувальними здібностями

#### Кастомні дроїди:
- Введення унікального імені
- Вибір типу дроїда
- Попередній перегляд характеристик
- Валідація параметрів

---

## 🎨 Візуальна система

### 🌈 Кольорова схема
- 🔴 **Червоний**: Штурмові дроїди, атаки, критичні стани
- 🔵 **Синій**: Танк дроїди, захисні дії, броня
- 🟣 **Фіолетовий**: Снайперські дроїди, критичні удари
- 🟢 **Зелений**: Лікувальні дроїди, лікування, високе здоров'я
- 🟡 **Жовтий**: Раунди, попередження, меню навігації

### 📊 Візуальні елементи

#### Health Bars:
```
[##########..........] 100/200 (50%)  # Зелений
[######..............] 60/200 (30%)   # Жовтий
[##..................] 20/200 (10%)   # Червоний
```

#### Типи дроїдів:
```
[ASLT] - Assault Droid
[TANK] - Tank Droid
[SNPR] - Sniper Droid
[HEAL] - Healer Droid
```

#### Спецефекти:
```
>>> ROUND 5 <<<
⚡ DOUBLE ATTACK!
💥 CRITICAL HIT!
💚 HEALING!
🛡️ ARMOR ABSORBED!
💀 XXX DESTROYED!
```

---

## 💾 Технічні особливості та архітектурні рішення

### 🏛️ Принципи OOP

#### 1. **Наслідування (Inheritance)**
```java
// Базовий клас
public abstract class Droid {
    protected String name;
    protected int health, maxHealth, damage, armor;

    public abstract void takeTurn(List<Droid> targets, List<Droid> allies);
}

// Дочірній клас
public class AssaultDroid extends Droid {
    private int consecutiveMisses = 0;

    @Override
    public void takeTurn(List<Droid> targets, List<Droid> allies) {
        // Реалізація подвійної атаки
    }
}
```

#### 2. **Поліморфізм (Polymorphism)**
```java
List<Droid> droids = Arrays.asList(
    new AssaultDroid("Alpha", 100, 25, 0),
    new TankDroid("Bravo", 150, 15, 20),
    new SniperDroid("Charlie", 50, 50, 0)
);

// Один виклик - різна поведінка
for (Droid droid : droids) {
    droid.takeTurn(enemies, allies); // Кожен тип діє по-своєму
}
```

#### 3. **Інкапсуляція (Encapsulation)**
```java
public class Droid {
    private int health;          // Приватне поле
    private final int maxHealth; // Незмінна константа

    public int getHealth() { return health; }                    // Getter
    public void setHealth(int h) { health = Math.max(0, h); }   // Валідований Setter
    public double getHealthPercentage() {                        // Обчислювана властивість
        return (double) health / maxHealth * 100;
    }
}
```

#### 4. **Абстракція (Abstraction)**
```java
public interface Battle {
    void start();
    boolean isFinished();
    List<Droid> getWinners();
}

// Різні реалізації одного інтерфейсу
public class OneVsOne implements Battle { /* ... */ }
public class TeamBattle implements Battle { /* ... */ }
```

### 🔧 Design Patterns

#### 1. **Factory Pattern**
```java
public class DroidFactory {
    public static Droid createDroid(String type, String name) {
        switch (type.toLowerCase()) {
            case "assault": return new AssaultDroid(name, 100, 25, 0);
            case "tank":    return new TankDroid(name, 150, 15, 20);
            case "sniper":  return new SniperDroid(name, 50, 50, 0);
            case "healer":  return new HealerDroid(name, 75, 10, 0);
            default: throw new IllegalArgumentException("Unknown droid type");
        }
    }
}
```

#### 2. **Strategy Pattern**
```java
// Різні стратегії через реалізацію інтерфейсу
Battle duel = new OneVsOne(droid1, droid2);      // Стратегія дуелі
Battle team = new TeamBattle(team1, team2);      // Стратегія команди
```

#### 3. **Observer Pattern**
```java
public class BattleVisualizer {
    public static void displayRound(int round) { /* візуалізація */ }
    public static void displayAttack(Droid attacker, Droid target, int damage) { /* анімація */ }
    public static void displayHealing(Droid healer, Droid target, int amount) { /* ефект */ }
}
```

### 📝 Система збереження та логування

#### Структура лог-файлу:
```json
{
  "timestamp": "2024-09-16_14:30:45",
  "battleType": "OneVsOne",
  "participants": [
    {"name": "Destroyer", "type": "AssaultDroid", "stats": "100HP/25DMG"},
    {"name": "Guardian", "type": "TankDroid", "stats": "150HP/15DMG/20ARM"}
  ],
  "rounds": [
    {
      "round": 1,
      "actions": [
        {"actor": "Destroyer", "action": "attack", "target": "Guardian", "damage": 25, "result": "15 absorbed by armor"}
      ]
    }
  ],
  "winner": "Guardian",
  "duration": "45 seconds"
}
```

#### Автоматичне управління файлами:
```java
public class BattleSaver {
    private static final String LOG_DIR = "logs/";

    static {
        // Автоматичне створення директорії
        new File(LOG_DIR).mkdirs();
    }

    public static void saveBattle(Battle battle) {
        String filename = generateTimestampedFilename();
        // Збереження з обробкою помилок
    }
}
```

---

## 🧠 Логіка бойової системи

### ⚔️ Алгоритм бою

#### 1. **Ініціалізація**
```java
public void start() {
    BattleVisualizer.displayBattleStart(participants);

    while (!isFinished()) {
        round++;
        BattleVisualizer.displayRound(round);

        for (Droid droid : getAliveDroids()) {
            if (droid.isAlive()) {
                executeTurn(droid);
            }
        }
    }

    announceWinner();
}
```

#### 2. **Система ходів**
```java
private void executeTurn(Droid current) {
    List<Droid> enemies = getEnemies(current);
    List<Droid> allies = getAllies(current);

    // Поліморфний виклик - кожен тип діє по-своєму
    current.takeTurn(enemies, allies);

    // Перевірка завершення бою
    if (enemies.stream().noneMatch(Droid::isAlive)) {
        battleFinished = true;
    }
}
```

#### 3. **Спеціальні здібності**

**Assault Droid - Double Attack:**
```java
@Override
public void takeTurn(List<Droid> enemies, List<Droid> allies) {
    Droid target = selectRandomTarget(enemies);

    // Базова атака
    int damage = attack(target);

    // Шанс подвійної атаки (зростає при невдачах)
    int doubleAttackChance = 30 + (consecutiveMisses * 10);
    if (random.nextInt(100) < doubleAttackChance) {
        BattleVisualizer.displaySpecialAbility("DOUBLE ATTACK!");
        attack(target); // Повторна атака
        consecutiveMisses = 0;
    } else {
        consecutiveMisses++;
    }
}
```

**Tank Droid - Armor Degradation:**
```java
@Override
public int takeDamage(int damage) {
    int absorbed = Math.min(damage, armor);
    int actualDamage = damage - absorbed;

    // Зброя деградує від пошкоджень
    if (absorbed > 0) {
        armor = Math.max(0, armor - 2);
        BattleVisualizer.displayArmorAbsorption(absorbed);
    }

    health -= actualDamage;
    return actualDamage;
}
```

**Sniper Droid - Critical Hits:**
```java
private int attack(Droid target) {
    int finalDamage = damage;

    // 30% шанс критичного удару
    if (random.nextInt(100) < 30) {
        finalDamage += 20; // +20 критичного урону
        BattleVisualizer.displayCriticalHit();
    }

    return target.takeDamage(finalDamage);
}
```

**Healer Droid - Smart Healing:**
```java
@Override
public void takeTurn(List<Droid> enemies, List<Droid> allies) {
    // 50% шанс лікування
    if (random.nextInt(100) < 50) {
        Droid healTarget = findMostWoundedAlly(allies);
        if (healTarget != null) {
            heal(healTarget, 25);
        }
    } else {
        // Якщо не лікує - атакує
        Droid target = selectRandomTarget(enemies);
        attack(target);
    }
}
```

### 🎯 Штучний інтелект

#### Target Selection Algorithm:
```java
private Droid selectRandomTarget(List<Droid> enemies) {
    List<Droid> aliveEnemies = enemies.stream()
        .filter(Droid::isAlive)
        .collect(Collectors.toList());

    if (aliveEnemies.isEmpty()) return null;

    // Випадковий вибір для справедливості
    return aliveEnemies.get(random.nextInt(aliveEnemies.size()));
}

private Droid findMostWoundedAlly(List<Droid> allies) {
    return allies.stream()
        .filter(Droid::isAlive)
        .filter(ally -> ally.getHealth() < ally.getMaxHealth())
        .min(Comparator.comparingDouble(Droid::getHealthPercentage))
        .orElse(null);
}
```

---

## 🔒 Обробка помилок та валідація

### 🛡️ Захист вводу
```java
public static Droid createCustomDroid() {
    String name;
    do {
        System.out.print("Enter droid name: ");
        name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Name cannot be empty!");
        }
    } while (name.isEmpty());

    // Валідація типу дроїда
    String type = selectValidDroidType();

    return DroidFactory.createDroid(type, name);
}
```

### 💾 Файлова система
```java
public static void saveBattle(Battle battle) {
    try {
        // Автоматичне створення директорії
        File logDir = new File(LOG_DIR);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        String filename = generateSafeFilename();
        Files.write(Paths.get(LOG_DIR + filename),
                   battleData.getBytes(),
                   StandardOpenOption.CREATE,
                   StandardOpenOption.WRITE);

    } catch (IOException e) {
        System.err.println("❌ Error saving battle: " + e.getMessage());
    }
}
```

### 🏥 Цілісність даних
```java
public void setHealth(int newHealth) {
    // Захист від від'ємних значень
    this.health = Math.max(0, Math.min(newHealth, maxHealth));
}

public int takeDamage(int damage) {
    if (damage < 0) {
        throw new IllegalArgumentException("Damage cannot be negative");
    }

    int actualDamage = Math.min(damage, health);
    health -= actualDamage;
    return actualDamage;
}
```

---

## 📊 Приклади використання

### 🎮 Сценарій повної гри

#### 1. Запуск та меню
```
🤖 === DROID BATTLE ARENA ===

Choose game mode:
1. 🤺 Duel (1 vs 1)
2. ⚔️ Team Battle
3. 📼 Watch Replay
4. ❌ Exit

Enter choice: 1
```

#### 2. Створення дроїдів
```
Creating Droid #1:
1. Use template
2. Create custom

Enter choice: 2

Enter droid name: Alpha
Select type:
1. [ASLT] Assault (100HP/25DMG) - Fast attacker
2. [TANK] Tank (150HP/15DMG/20ARM) - Damage absorber
3. [SNPR] Sniper (50HP/50DMG) - Critical striker
4. [HEAL] Healer (75HP/10DMG/25HEAL) - Team support

Enter choice: 1
✅ [ASLT] Alpha created!
```

#### 3. Бойова візуалізація
```
🤺 === DUEL STARTING ===

Droid #1: [ASLT] Alpha     [####################] 100/100 (100%)
   VS
Droid #2: [TANK] Guardian  [####################] 150/150 (100%)

>>> ROUND 1 <<<
[ASLT] Alpha attacks [TANK] Guardian for 25 damage!
🛡️ [TANK] Guardian's armor absorbs 20 damage!
[TANK] Guardian takes 5 damage! (145/150 HP)

[TANK] Guardian attacks [ASLT] Alpha for 15 damage!
[ASLT] Alpha takes 15 damage! (85/100 HP)

>>> ROUND 2 <<<
⚡ [ASLT] Alpha: DOUBLE ATTACK!
[ASLT] Alpha attacks [TANK] Guardian for 25 damage!
🛡️ Armor absorbs 18 damage! (Armor degraded to 18)
[TANK] Guardian takes 7 damage! (138/150 HP)

[ASLT] Alpha attacks [TANK] Guardian for 25 damage!
🛡️ Armor absorbs 16 damage! (Armor degraded to 16)
[TANK] Guardian takes 9 damage! (129/150 HP)
```

#### 4. Завершення та збереження
```
>>> ROUND 8 <<<
[ASLT] Alpha attacks [TANK] Guardian for 25 damage!
[TANK] Guardian takes 25 damage! (0/150 HP)
💀 [TANK] Guardian XXX DESTROYED!

🏆 === BATTLE FINISHED ===
🥇 Winner: [ASLT] Alpha (15/100 HP remaining)

📝 Battle saved to: logs/duel_2024-09-16_14-30-45.log
⏱️ Battle duration: 2 minutes 15 seconds
```

### 📼 Replay система
```
📼 === BATTLE REPLAYS ===

Found 5 saved battles:
1. duel_2024-09-16_14-30-45.log    (just now)     [ASLT vs TANK]
2. team_2024-09-16_14-15-20.log    (15m ago)      [Team 2v2]
3. duel_2024-09-16_13-45-10.log    (45m ago)      [SNPR vs HEAL]
4. team_2024-09-16_12-30-30.log    (2h ago)       [Team 3v3]
5. duel_2024-09-16_11-15-45.log    (3h ago)       [TANK vs ASLT]

Select replay (1-5): 1

Replay speed:
1. ⚡ Instant
2. 🏃 Fast (0.5s delay)
3. 🚶 Normal (1s delay)
4. 🐌 Slow (2s delay)

Enter choice: 3

🎬 Starting replay...
```

---

## 🏆 Результати та висновки

### ✅ Досягнуті цілі

#### 1. **Об'єктно-орієнтоване програмування**
- ✅ **Наслідування**: Ієрархія класів Droid → AssaultDroid/TankDroid/etc.
- ✅ **Поліморфізм**: Метод `takeTurn()` з різною реалізацією для кожного типу
- ✅ **Інкапсуляція**: Приватні поля з публічними методами доступу
- ✅ **Абстракція**: Інтерфейс `Battle` для різних режимів гри

#### 2. **Архітектурні паттерни**
- ✅ **Factory Pattern**: Створення дроїдів через `DroidFactory`
- ✅ **Strategy Pattern**: Різні типи боїв через `Battle` інтерфейс
- ✅ **Observer Pattern**: Візуалізація через `BattleVisualizer`

#### 3. **Функціональність**
- ✅ **Інтерактивний інтерфейс**: Зручне меню та навігація
- ✅ **Система збереження**: Автоматичні логи всіх боїв
- ✅ **Replay функція**: Перегляд записаних битв з різною швидкістю
- ✅ **Кольорова візуалізація**: Приємний інтерфейс в терміналі

#### 4. **Надійність**
- ✅ **Валідація вводу**: Захист від некоректних даних
- ✅ **Обробка помилок**: Try-catch для файлових операцій
- ✅ **Цілісність даних**: Захист від від'ємних значень здоров'я

### 📈 Статистика проекту

| Компонент | Кількість |
|-----------|-----------|
| **Класів** | 12 |
| **Інтерфейсів** | 1 |
| **Пакетів** | 4 |
| **Рядків коду** | ~1500 |
| **Типів дроїдів** | 4 |
| **Режимів гри** | 3 |
| **Design Patterns** | 3 |

### 🎓 Навички що розвинуті

#### **Технічні навички:**
- ☑️ Проектування класової ієрархії
- ☑️ Реалізація поліморфізму та наслідування
- ☑️ Робота з файловою системою Java
- ☑️ Обробка винятків та валідація
- ☑️ Організація коду в пакети
- ☑️ Використання колекцій (List, Stream API)

#### **Архітектурні навички:**
- ☑️ Застосування Design Patterns
- ☑️ Розділення відповідальностей між класами
- ☑️ Створення розширюваної архітектури
- ☑️ Інтерфейси та абстракція

#### **UX/UI навички:**
- ☑️ Проектування інтуїтивного інтерфейсу
- ☑️ Кольорова візуалізація в консолі
- ☑️ Інтерактивна навігація
- ☑️ Feedback для користувача

### 🚀 Можливості для розширення

#### **Короткострокові покращення:**
1. **Нові типи дроїдів**: Stealth, Engineer, Bomber
2. **Більше спецздібностей**: Shields, Teleport, Area damage
3. **Система рівнів**: Experience points, upgrades
4. **Різні арени**: Environmental effects, obstacles

#### **Довгострокові розширення:**
1. **Мережева гра**: Multiplayer battles
2. **Графічний інтерфейс**: JavaFX або Swing GUI
3. **ШІ противники**: Smart AI strategies
4. **Турнірний режим**: Bracket-style competitions

### 💡 Отримані знання

#### **Принципи чистого коду:**
- Читабельність та підтримуваність коду
- Змістовні назви класів та методів
- Коментарі лише де необхідно
- Принцип єдиної відповідальності (SRP)

#### **Розуміння ООП:**
- Коли використовувати наслідування vs композицію
- Переваги поліморфізму для розширюваності
- Важливість інкапсуляції для безпеки даних
- Абстракція як спосіб спрощення складності

#### **Практичні навички:**
- Робота з Git для версійного контролю
- Структурування проекту в пакети
- Тестування та налагодження
- Документування коду

---

## 👤 Інформація про автора

**Автор**: Ruslan Shevchuk
**Курс**: Програмування та програмні засоби
**Лабораторна робота**: №3 - ООП в Java
**Дата створення**: Вересень 2024
**Версія**: 1.2

### 📞 Контакти
- **GitHub**: [github.com/ryl1k](https://github.com/ryl1k)
- **Email**: [ruslan.shevchuk@student.university.edu](mailto:ruslan.shevchuk@student.university.edu)

### 🏫 Навчальний заклад
**Університет**: [Назва університету]
**Факультет**: Інформаційних технологій
**Спеціальність**: Програмна інженерія
**Група**: [Номер групи]

---

**🤖 Цей README створено з використанням штучного інтелекту для максимально детального опису проекту та демонстрації розуміння принципів ООП.**