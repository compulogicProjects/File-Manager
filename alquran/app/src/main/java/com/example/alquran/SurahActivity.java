package com.example.alquran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SurahActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah);

        recyclerView=findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String[] suratname=new String[] {"Surah Al Fatihah","Surah Al-Baqra","Surah Al-Imran",
                "Surah An-Nisa'","Surah Al-Ma'idah","Surah Al An'am","Surah Al-A'raf",
                "Surah Al-Anfal","Surah Al Tawabah","Surah Younus","Surah Hud","Surah Yusuf",
                "Surah Ar-Ra'd","Surah Ibrahim","Surah Al-Hijr","Surah An-Nahi","Surah Al-Isra",
                "Surah Al-kahf","Surah Maryam","Surah TaHa","Surah Al-Anbia'","Surah Al-Hajj",
                "Surah Al-Mu'minun","Surah An-Nur","Surah Al-Furqan","Surah Ash-Shu'ara'",
                "Surah An-Naml","Surah Al-Qasas","Surah Al-Ankabut","Surah Ar-Rum","Surah Luqman",
                "Surah As-Sajdah","Surah Al-Ahzab","Surah Al-Saba'","Surah Al-Fatir","Surah Ya Sin",
                "Surah As-Saffat","Surah Sad","Surah Az-Zumar","Surah Ghafir","Surah Ha Mim",
                "Surah Ash-Shura","Surah Az-Zukhruf","Surah Ad-Dukhan","Surah Al-Jathiyah",
                "Surah Al-Ahqaf","Surah Muhammad","Surah Al-Fath","Surah Al-Hujurat","Surah Qaf",
                "Surah Ad-Dhariat","Surah At-Tur","Surah An-Najm","Surah Al-Qamar","Surah Ar-Rahman",
                "Surah Al-Waqi'ah","Surah Al-Hadid","Surah Al-Mujadilah","Surah Al-Hashr",
                "Surah Al-Mumtahanah","Surah As-Saff","Surah Al-Jumm'ah","Surah Al-Munafiqun",
                "Surah Al-Taghabun","Surah At-Talaq","Surah At-Tahrim","Surah Al-Mulk","Surah Al-Qalam",
                "Surah Al-Haqqah","Surah Al-Ma'arij","Surah Nuh","Surah Al-Jinn","Surah Al-Muzzammil",
                "Surah Al-Muddaththir","Surah Al-Qiyamah","Surah Al-Insan","Surah Al-Mursalat",
                "Surah An-Naba'","Surah An-Nazi'at","Surah'Abasa","Surah At-Takwir","Surah Al-Infitar",
                "Surah Al-Mutaffifeen","Surah Al-Inshiqaq","Surah Al-Buruj","Surah At-Tariq",
                "Surah Al-A'la","Surah Al-Ghasiyah","Surah Al-Fajr","Surah Al-Balad","Surah Ash-Shams",
                "Surah Al-Lail","Surah Ad-Duha","Surah Ash-Sharh","Surah At-Teen","Surah Al-'Alaq",
                "Surah Al-Qadr","Surah Al-Bayyinah","Surah Al-Zilzal","Surah Al-'Adiyat","Surah Al-Qari'ah",
                "Surah Al-Takathur","Surah Al-'Asr","Surah Al-Humazah","Surah Al-Fil","Surah Al-Quraish",
                "Surah Al-Ma'un","Surah Al-Kauthar","Surah Al-Kafirun","Surah An-Nasr","Surah Al-Lahab",
                "Surah Al-Ikhlas","Surah Al-Falaq","Surah An-Naas"};
        String[] suratnamemeaning = new String[] {"The Opening","The Cow","The Family of Imran",
                "The Women","The Food","The Castle","The Heights","The Spils of War",
                "The Repentance","Jonah","Hud","Joseph('Alaihis Salaam)","The Thunder",
                "Abraham('Allahis Saleem)","The Rock","The Bee","The Night Journey","The cave",
                "Maryam","Ta Ha","The Porphets","The Pilgrimage","The Beleivers","The Light",
                "The Discrimination","The Poests","The Naml","The Narrative","The Spider",
                "The Romans ","Luqman","The Adoration","The Allies","The Saba","The Orginatior",
                "Ya Sin","Those Ranging in Ranks","Sad","The Companies","The Forgiver(God)",
                "Ha Mim","Counsel","Gold","The Drought","The Kneeling","The Sandhills",
                "Muhammad (S.A.W)","The Victory","The Apartments","Qaf","The Scatterers",
                "The Mountain","The Unfolding","The Moon","The Beneficent","The Event","Iron",
                "The Pleading Woman","The Banishment","The Women Who is Examined","The Ranks",
                "The Congregation","The Hypocrites","The Manifestation of Losses","Divorce",
                "The Prohibition","The Kingdom","The Pen","The Sure Truth","The Ways of Asscent",
                "The Noah","The Jinn","The Mantled One","The Clothed One","The Resurrection",
                "The Man","Those Sent Forth","The Announcement","Those who Yearn","He Frowned","The Folding Up",
                "The Cleaving","The Defrauders","The Brusting Asunder","The Stars","The Comer by Night",
                "The Most High","The Overwhelming Evient","The Daybreak","The City","The Sun","The Night",
                "The Brightness of the Day","The Expansion","The Fig","The Clot","The Majesty","The Clear Evidence",
                "The Shaking","The Assaulaters","The Calamity","The Abundance of Wealth","The time",
                "The Slanderer","The Elephant","The Quraish","Acts of Kindness","The Abundance of Good",
                "The Disbelievers","The Help","The Flame","Al-Ikhlas(The Unity)","The Daybreak",
                "The Men"};

        listAdapter= new ListAdapter(suratname,suratnamemeaning,this);
        recyclerView.setAdapter(listAdapter);

    }
}