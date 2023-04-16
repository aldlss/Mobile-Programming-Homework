package cn.suwako.the2023plus

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val ButtonTexts = listOf(
    "以和为贵",
    "万紫千红",
    "一鸣惊人",
    "鸟语花香",
    "五湖四海",
    "空穴来风",
    "不言而喻",
    "上善若水",
)

data class ButtonData (
    var text: String,
    var textIdx: Int,
    var clickable: Boolean,
    var clicked: Boolean = false,
    var color: Int = 0,
)

// 选出 num 个成语
fun randomNums(num: Int): List<Int> {
    if (num > ButtonTexts.count()) {
        return listOf();
    }
    val range = 0 until ButtonTexts.count();
    return range.shuffled().take(num);
}

// 0 ~ num * 4，在 num 个成语里选每个字的出现顺序
fun randomText(num: Int): List<Int> {
    val range = 0 until num * 4;
    return range.shuffled();
}

class MainViewModel: ViewModel() {

    // 使用的成语个数
    val idiomCount = 4;
    var gameOver by mutableStateOf(false);
    var clickCount by mutableStateOf(0);
    private var finishCount = 0;
    private val _buttonData = mutableListOf<MutableState<ButtonData>>();
    val buttonData: MutableList<MutableState<ButtonData>>
        get() = _buttonData;

    init {
        repeat(idiomCount * 4) {
            _buttonData.add(mutableStateOf(ButtonData("", 0, clickable = true)))
        }
        reset();
    }

    fun reset() {
        finishCount = 0;
        clickCount = 0;
        gameOver = false;
        val randomNums = randomNums(idiomCount);
        val randomText = randomText(idiomCount);
        for (i in 0 until  idiomCount * 4) {
            _buttonData[i].value = ButtonData(
                text = ButtonTexts[randomNums[randomText[i] / 4]][randomText[i] % 4].toString(),
                textIdx = randomText[i] / 4,
                clickable = true,
                clicked = false,
                color = 0,
            )
        }
    }

    private var nowNum = -1;
    private var success = true;
    private val clickList = mutableListOf<Int>();
    fun clickButton(idx: Int) {
        if (_buttonData[idx].value.clicked || clickList.count() >= 4) {
            return;
        }
        if (nowNum == -1) {
            nowNum = _buttonData[idx].value.textIdx;
        }
        else if(nowNum != _buttonData[idx].value.textIdx) {
            success = false;
            nowNum = -2;
        }
        ++clickCount;
        _buttonData[idx].value = _buttonData[idx].value.copy(clicked = true);
        clickList.add(idx);
        _buttonData[idx].value.color = _buttonData[idx].value.color xor 1;
        if(clickList.count() == 4)
        {
            viewModelScope.launch {
                dealResult();
            }
        }
    }

    // 延迟 800ms，如果成功则显示笑脸，否则恢复原来的颜色以及点击状态
    private suspend fun dealResult() {
        delay(800)
        if (success) {
            finishCount++;
            for (i in clickList) {
                _buttonData[i].value = _buttonData[i].value.copy(text = "🤗", clickable = false);
            }
            if (finishCount == idiomCount) {
                gameOver = true;
            }
        } else {
            for (i in clickList) {
                _buttonData[i].value = _buttonData[i].value.copy(clicked = false);
                _buttonData[i].value.color = _buttonData[i].value.color xor 1;
            }
        }
        clickList.clear();
        nowNum = -1;
        success = true;
    }
}